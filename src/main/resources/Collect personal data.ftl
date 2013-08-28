<html>
<body>
<h2>Collect personal data</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
<form action="complete" method="POST" enctype="multipart/form-data">
id: <input type="text" name="id" /><BR/>
payInCash: <input type="text" name="payInCash" /><BR/>
name: <input type="text" name="name" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>