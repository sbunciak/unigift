<html>
<body>
<h2>Summarize research</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
favouriteProducts: ${favouriteProducts}<BR/>
needs: ${needs}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
summary: <input type="text" name="summary" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>