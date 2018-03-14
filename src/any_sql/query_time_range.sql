select 
	to_char(max(t.time_key),'yyyymmdd') as starttime
	,to_char(sysdate-1,'yyyymmdd') as endtime
from t_inerface_data  t