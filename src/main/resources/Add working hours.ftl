<html>
<body>
<h2>Add working hours</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
employee: ${employee}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
hours: <input type="text" name="hours" /><BR/>
hourPay: <input type="text" name="hourPay" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>