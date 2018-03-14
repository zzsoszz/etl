select vcSetNumber as vcsetnumber,
'${starttime}' as starttime ,
'${endtime}' as endtime
  from tblinteractioncatalog

 where

	(
		'${starttime}' <= convert(char(8), dtMinStartTime, 112) + convert(char(8), dtMinStartTime, 108)
		and 
		'${endtime}' >=convert(char(8), dtMinStartTime, 112) + convert(char(8), dtMinStartTime, 108)
	)
	or
	(
	   '${starttime}' <= convert(char(8), dtMaxStartTime, 112) + convert(char(8), dtMaxStartTime, 108)
		and
	   '${endtime}' >=convert(char(8), dtMaxStartTime, 112) + convert(char(8), dtMaxStartTime, 108)
	)
	or 
	(
		'${starttime}' >=convert(char(8), dtMinStartTime, 112) + convert(char(8), dtMinStartTime, 108)
		and 
		'${endtime}' <=convert(char(8), dtMaxStartTime, 112) + convert(char(8), dtMaxStartTime, 108)
	)