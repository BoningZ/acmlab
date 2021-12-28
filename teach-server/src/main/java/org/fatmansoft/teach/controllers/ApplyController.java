package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Application;
import org.fatmansoft.teach.models.Contest;
import org.fatmansoft.teach.models.Ranking;
import org.fatmansoft.teach.models.Team;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.ApplicationRepository;
import org.fatmansoft.teach.repository.ContestRepository;
import org.fatmansoft.teach.repository.RankingRepository;
import org.fatmansoft.teach.repository.TeamRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/apply")
public class ApplyController {
    @Autowired
    RankingRepository rankingRepository;
    @Autowired
    ContestRepository contestRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    TeamRepository teamRepository;


    @PostMapping("/getSeasonContestList")//获取某个赛季比赛列表 以及排在此队伍前后的已填报志愿的队伍
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public DataResponse getSeasonContestList(@Valid @RequestBody DataRequest dataRequest) {
        Integer season=dataRequest.getInteger("season");
        Integer id=dataRequest.getInteger("id");
        Team team=teamRepository.getById(id);
        long score=getSeasonScore(team,season);
        List cList=contestRepository.getContestBySeason(season);
        List dataList=new ArrayList();
        for(Object o:cList){
            Contest c=(Contest)o;
            Map m=CommonMethod.objectToMap(c);
            List applied=getRankedApplies(season,c.getRankInSeason());
            List front=new ArrayList(), back=new ArrayList();
            for(Object a:applied){
                Team t=(Team)a;
                String info=t.getChinese()+"  "+t.getEnglish()+" :"+t.getCaptain().getChineseName()+","+t.getMember1().getChineseName()+","+t.getMember2().getChineseName();
                if(getSeasonScore(t,season)>score)front.add(info);
                else if(!t.equals(team)) back.add(info);
            }
            m.put("front",front); m.put("back",back);
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/getApplication")//获取某个队伍的志愿情况
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public DataResponse getApplication(@Valid @RequestBody DataRequest dataRequest) {
        Integer season=dataRequest.getInteger("season");
        Integer id=dataRequest.getInteger("id");
        Team team=teamRepository.getById(id);
        if(!rankingRepository.existsByTeamAndSeason(team,season))
            return CommonMethod.getReturnMessageError("此队伍未参加排位赛，不得填报志愿");

        Application application;
        try{application=applicationRepository.findApplicationByTeamAndSeason(team,season).get();}
        catch (Exception e){application=new Application(team,season); application.setAppList(0);applicationRepository.save(application);}
        Integer appList=application.getAppList();
        List dataList=new ArrayList();
        for(int i=1;i<30;i++)
            if((appList&(1<<i))>0)dataList.add(i);
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/submitApplication")
    @PreAuthorize(" hasRole('USER')")
    public DataResponse submitApplication(@Valid @RequestBody DataRequest dataRequest) {
        List list=dataRequest.getList("applications");
        Integer season=dataRequest.getInteger("season");
        Integer id=dataRequest.getInteger("id");
        Team team=teamRepository.getById(id);
        Application application=applicationRepository.findApplicationByTeamAndSeason(team,season).get();
        int newApp=0;
        for(Object o:list){
            Integer s=(Integer)o;
            newApp|=(1<<s);
        }
        application.setAppList(newApp);
        applicationRepository.save(application);
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/getAppliedTeamList")//获取某个比赛报名的队伍，有序。 若countInSeason为空则查询整个赛季 仅显示有志愿的队伍
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public DataResponse getAppliedTeamList(@Valid @RequestBody DataRequest dataRequest) {
        Integer season=dataRequest.getInteger("season");
        Integer countInSeason=dataRequest.getInteger("countInSeason");
        List list=getRankedApplies(season,countInSeason);
        List dataList=new ArrayList();
        for(Object o:list){
            Team t=(Team)o;
            Map m=CommonMethod.objectToMap(t);
            m.put("captain",CommonMethod.objectToMap(t.getCaptain()));
            m.put("member1",CommonMethod.objectToMap(t.getMember1()));
            m.put("member2",CommonMethod.objectToMap(t.getMember2()));
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    private List getRankedApplies(Integer season,Integer countInSeason){//获取某个比赛报名的队伍，有序 countInSeason为空则查询整个赛季
        int filter=0;
        if(countInSeason==null)filter=(1<<30)-1;
        else filter|=(1<<countInSeason);
        List rList=rankingRepository.getRankingsBySeason(season);
        List tList=new ArrayList();
        for(Object o:rList){
            Ranking r=(Ranking)o;
            tList.add(r.getTeam());
        }
        tList=new ArrayList(new LinkedHashSet(tList));
        tList.sort((Comparator<Team>) (o1, o2) -> {
            long s1=getSeasonScore(o1,season); long s2=getSeasonScore(o2,season);
            if (s1==s2) return 0;
            return s1< s2 ? 1 : -1;
        });
        List dataList=new ArrayList();
        for(Object o:tList){
            Team t=(Team)o;
            Application a;
            try{a=applicationRepository.findApplicationByTeamAndSeason(t,season).get();}
            catch (Exception e){continue;}
            if((a.getAppList()&filter)>0)dataList.add(t);
        }
        return dataList;
    }

    private Long getSeasonScore(Team team,Integer season){
        List rList=rankingRepository.getRankingsByTeamAndSeason(team,season);
        if(rList.size()==0)return (long)0;
        long score=0;
        for(Object o:rList){
            Ranking r=(Ranking)o;
            score=score+r.getScore()*10000000-r.getPenalty();
        }
        return score;
    }



}
