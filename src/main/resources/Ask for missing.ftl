<html>
<body>
<h2>Ask for missing</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
everythingArrived: ${everythingArrived}<BR/>
products: ${products}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>