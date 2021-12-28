import axios from "axios";
import { store } from "@/store/createStore.js"

function generalRequest(url, data) {
    return axios.post(
        url,
        {
            data: data
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.data
    })
}

function samplePost() {
    return generalRequest('/api/teach/admin', null)
}

function getMenuList() {
    return generalRequest('/api/teach/getMenuList', null)
}

function changePassword(data) {
    return generalRequest('/api/auth/changePassword', data)
}
function register(data){
    return generalRequest('/api/auth/signup',data)
}
function getProfile(data){
    return generalRequest('/api/teach/getProfile',data);
}
function submitProfile(data){
    return generalRequest('/api/teach/submitProfile',data);
}
function getMarks(data){
    return generalRequest('/api/interview/getMarks',data);
}
function submitMarks(data){
    return generalRequest('/api/interview/submitMarks',data);
}
function getInterviewees(data){
    return generalRequest('/api/interview/getInterviewees',data);
}
function formalize(data){
    return generalRequest('/api/interview/formalize',data);
}
function getContestList(data){
    return generalRequest('/api/contest/getContestList',data);
}
function submitContest(data){
    return generalRequest('/api/contest/submitContest',data);
}
function getContestInfo(data){
    return generalRequest('/api/contest/getContestInfo',data);
}
function getContestTypes(data){
    return generalRequest('/api/contest/getContestTypes',data);
}
function getTeamList(data){
    return generalRequest('/api/team/getTeamList',data);
}
function getTeamInfo(data){
    return generalRequest('/api/team/getTeamInfo',data);
}
function submitTeam(data){
    return generalRequest('/api/team/submitTeam',data);
}
function getStudentList(data){
    return generalRequest('/api/team/getStudentList',data);
}
function getRankingList(data){
    return generalRequest('/api/ranking/getRankingList',data);
}
function getRankingInfo(data){
    return generalRequest('/api/ranking/getRankingInfo',data);
}
function submitRanking(data){
    return generalRequest('/api/ranking/submitRanking',data);
}
function getAccess(data){
    return generalRequest('/api/team/getAccess',data);
}
function getSeasonContestList(data){
    return generalRequest('/api/apply/getSeasonContestList',data);
}
function getApplication(data){
    return generalRequest('/api/apply/getApplication',data);
}
function submitApplication(data){
    return generalRequest('/api/apply/submitApplication',data);
}
function getAppliedTeamList(data){
    return generalRequest('/api/apply/getAppliedTeamList',data);
}
function createProcess(data){
    return generalRequest('/api/process/create',data);
}
function getProcessList(data){
    return generalRequest('/api/process/getList',data);
}
function pushProcess(data){
    return generalRequest('/api/process/push',data);
}
function refreshHT(data){
    return generalRequest('/api/process/refreshHT',data);
}
function refreshA(data){
    return generalRequest('/api/process/refreshA',data);
}
function refreshFCR(data){
    return generalRequest('/api/process/refreshFCR',data);
}

//  company


export {
    samplePost,
    getMenuList,
    changePassword,register,
    getProfile,submitProfile,
    getMarks,submitMarks,
    getInterviewees,formalize,
    getContestList,getContestInfo,submitContest,getSeasonContestList,getContestTypes,
    getTeamList,submitTeam,getTeamInfo,getAccess,
    getStudentList,
    submitRanking,getRankingInfo,getRankingList,
    submitApplication,getApplication,getAppliedTeamList,
    createProcess,getProcessList,pushProcess,
    refreshHT,refreshA,refreshFCR,
}