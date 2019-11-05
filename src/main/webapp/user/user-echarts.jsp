<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>

<html lang="en">
<head>
    <title>Document</title>

</head>

<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script>
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    $.ajax({
        url:"${pageContext.request.contextPath}/user/echarts",
        type:"get",
        datatype:"json",
        success:function (data) {
            console.log(data);
            myChart.setOption({
                title: {
                    text: '上半年用户注册趋势'
                },
                tooltip: {},
                legend: {
                    data:['男','女']
                },
                xAxis: {
                    data: ["1月份","2月份","3月份","4月份","5月份","6月份"]
                },
                yAxis: {},
                series: [{
                    name: '男',
                    type: 'line',//bar:柱状图
                    data: data.nan
                },{
                    name: '女',
                    type: 'line',//bar:柱状图
                    data: data.nv
                }]
            });
        }
    })


</script>

</body>
</html>