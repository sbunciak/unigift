<html>
<body>
<h2>Check stock</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
product: ${product}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
onStock: <input type="text" name="onStock" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>