<html>
<body>
<h2>Analyze complaints</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
complaint: ${complaint}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
issueType: <input type="text" name="issueType" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>