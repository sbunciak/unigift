<html>
<body>
<h2>Check arrival</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
products: ${products}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
everythingArrived: <input type="text" name="everythingArrived" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>