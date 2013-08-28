<html>
<body>
<h2>Design Product</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
summary: ${summary}<BR/>
needs: ${needs}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
description: <input type="text" name="description" /><BR/>
name: <input type="text" name="name" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>