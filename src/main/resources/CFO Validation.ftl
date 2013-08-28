<html>
<body>
<h2>CFO Validation</h2>
<hr>
<#if task.descriptions[0]??>
Description: ${task.descriptions[0].text}<BR/>
</#if>
hours: ${hours}<BR/>
pay: ${pay}<BR/>
employee: ${employee}<BR/>
hourPay: ${hourPay}<BR/>
<form action="complete" method="POST" enctype="multipart/form-data">
<BR/>
<input type="submit" name="outcome" value="Complete"/>
</form>
</body>
</html>