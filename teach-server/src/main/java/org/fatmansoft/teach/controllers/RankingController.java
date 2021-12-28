package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Ranking;
import org.fatmansoft.teach.models.Team;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.RankingRepository;
import org.fatmansoft.teach.repository.TeamRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ranking")
public class RankingController {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RankingRepository rankingRepository;

    @PostMapping("/submitRanking")
    @PreAuthorize("hasRole('ADMIN') ")
    public DataResponse submitRanking(@Valid @RequestBody DataRequest dataRequest){
        Map form=dataRequest.getMap("form");
        Integer season=(Integer)form.get("season");
        Integer countInSeason=(Integer)form.get("countInSeason");
        List rList=(List)form.get("ranks");
        for(int i=0;i<rList.size();i++){
            Map m=(Map)rList.get(i);
            Integer id=(Integer)m.get("id");
            Team team=teamRepository.getById(id);
            List acs=(List)m.get("acs");
            Integer ac=0;
            for(int j=0;j<acs.size();j++){char ch=((String)acs.get(j)).charAt(0);ac+=1<<(ch-'A');}
            Integer penalty;
            if(m.get("penalty").getClass().equals(String.class))penalty=Integer.parseInt((String)m.get("penalty"));
            else penalty=(Integer)m.get("penalty");
            Ranking ranking;
            try{ranking=rankingRepository.getByTeamAndSeasonAndCountInSeason(team,season,countInSeason).get();
                ranking.setPenalty(penalty);
                ranking.setSolved(ac);
            }catch (Exception e){ranking=new Ranking(team,ac,season,countInSeason,penalty);}
            rankingRepository.save(ranking);
        }
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/getRankingInfo")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse getRankingInfo(@Valid @RequestBody DataRequest dataRequest){
        Integer season=dataRequest.getInteger("season");
        Integer countInSeason=dataRequest.getInteger("countInSeason");
        Map form=new HashMap();
        if(countInSeason==null){//新建时，初始化所有参加排位的队伍，创建空排位结果
            countInSeason=getCountsInSeason(season)+1;
            List tList=teamRepository.findTeamsByActive(true);
            for (Object o : tList) {
                Team team = (Team) o;
                Ranking ranking=new Ranking(team,0,season,countInSeason,0);
                rankingRepository.save(ranking);
            }
        }
        form.put("season",season);
        form.put("countInSeason",countInSeason);
        List ranks=new ArrayList();
        List rList=rankingRepository.getRankingsBySeasonAndCountInSeason(season,countInSeason);
        for(int i=0;i<rList.size();i++){
            Ranking ranking=(Ranking)rList.get(i);
            Team team=ranking.getTeam();
            Map m=new HashMap();
            m.put("id",team.getId());
            m.put("name",team.getChinese()+" "+team.getEnglish());
            m.put("penalty",ranking.getPenalty());
            List acs=new ArrayList();
            Integer solved=ranking.getSolved();
            for(int j=0;j<13;j++)if((solved&(1<<j))>0)acs.add(""+(char)('A'+j));
            m.put("acs",acs);
            ranks.add(m);
        }
        form.put("ranks",ranks);
        return CommonMethod.getReturnData(form);
    }

    @PostMapping("/getRankingList")//获取某赛季全部排位赛情况
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public DataResponse getRankingList(@Valid @RequestBody DataRequest dataRequest){
        Integer season=dataRequest.getInteger("season");
        List dataList=new ArrayList();
        Integer counts=getCountsInSeason(season);
        for(int i=1;i<=counts;i++){
            Map m=new HashMap();
            m.put("countInSeason",i);m.put("season",season);
            List rList=rankingRepository.getRankingsBySeasonAndCountInSeason(season,i);
            List ranks=new ArrayList();
            for(int j=0;j<rList.size();j++){
                Map m1=new HashMap();
                Ranking ranking=(Ranking)rList.get(j);
                Team team=ranking.getTeam();
                m1.put("score",ranking.getScore());
                m1.put("name",team.getChinese()+" "+team.getEnglish());
                m1.put("penalty",ranking.getPenalty());
                String acs="";
                Integer solved=ranking.getSolved();
                for(int k=0;k<13;k++)if((solved&(1<<k))>0)acs+=(char)('A'+k);
                m1.put("acs",acs);
                ranks.add(m1);
            }
            ranks.sort((Comparator<HashMap>) (o1, o2) -> {
                if (o1.equals(o2)) return 0;
                if(!(o1.get("score")).equals(o2.get("score")))
                    return (Integer)o1.get("score") < (Integer)o2.get("score") ? 1 : -1;
                else return (Integer)o1.get("penalty")>(Integer)o2.get("penalty")?1:-1;
            });
            m.put("ranks",ranks);
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    private Integer getCountsInSeason(Integer season){
        List rList=rankingRepository.getRankingsBySeason(season);
        Integer res=0;
        for (Object o : rList) {
            int cnt = ((Ranking) o).getCountInSeason();
            if (cnt > res) res = cnt;
        }
        return res;
    }


}
