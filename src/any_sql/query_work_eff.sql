 select agentid,substring(datehour,1,8) as time_key ,
       sum(readytime + talktime + aftercallworktime) worktime,
       (case
         when sum(readytime + mediaworktime + precalltime +
                  aftercallworktime + talktime) > sum(logintime) then
          sum(readytime + mediaworktime + precalltime + aftercallworktime +
              talktime)
         else
          sum(logintime)
       end) logintime
  from vw_rpt_agentstatus_halfhour
  where datehour>='${starttime}'
  and datehour<='${endtime}'
  group by agentid,substring(datehour,1,8)
  order by agentid,substring(datehour,1,8)