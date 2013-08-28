<html>
<body>
<h2>Check quality</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
<form action="complete" method="POST" enctype="multipart/form-data">
qualityOk: <input type="text" name="qualityOk" /><BR/>
qualityMessage: <input type="text" name="qualityMessage" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>