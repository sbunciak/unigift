<html>
<body>
<h2>Obtain complaint</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
product: ${product}<BR/>
description: ${description}<BR/>
contact: ${contact}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>