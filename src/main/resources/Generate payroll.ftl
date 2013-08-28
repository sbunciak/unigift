<html>
<body>
<h2>Generate payroll</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
hours: ${hours}<BR/>
pay: ${pay}<BR/>
employee: ${employee}<BR/>
hourPay: ${hourPay}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
payroll: <input type="text" name="payroll" /><BR/>
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>