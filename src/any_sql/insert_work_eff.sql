insert into t_inerface_data
  (user_id,
   time_key,
   N_WORK_TIME,
   N_INEFFECT_WORK_TIME
   )
values
  (
   '${agentid}'
   ,to_date('${time_key}','yyyymmdd')
   ,'${worktime}'
   ,'${logintime}'
   )
