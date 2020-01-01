$(document).ready(function () {
    //模态框-确认按钮-隐藏窗口
    $("#addCommit").click(function () {
        $('#myModal').modal('hide')
    });
    fillTable();
});


var baseUrl = 'http://127.0.0.1:8080/pageQueryGP';

var fillTable= function(){
    //先销毁表格
    $('#table').bootstrapTable("destroy");
    $('#table').bootstrapTable({
        url: baseUrl,
        method: "get",
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        toggle: "table",
        pagination: true,//是否分页
        pageSize: 10,//单页记录数
        pageList: [10, 20, 30],//可选择单页记录数
        smartDisplay: false,
        // search: true,                      //是否显示表格搜索
        // showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        sortable: true, //是否启用排序
        sortOrder: "desc", //排序方式
        toolbar: "#toolbar",
        queryParams: function (params) {
            return {
                page: params.offset / params.limit,  //页码
                size: params.limit,   //页面大小
                queryContext: $("#queryContext").val(),
            };
        },
        columns: [
            // {checkbox: true},
            {field: 'id', title: '编码'},
            {field: 'name', title: '名称'},
            {field: 'count', title: '数量'},
            {
                field:'action',
                title:'操作',
                // events:'operateEvents',
                formatter:'operateFormatter',       //自定义表格内容，字符串内是方法名称
            }
        ]
    });
}


function operateFormatter(value, row, index) {
    var id = row.id;
    return [
        "<a href=\"xiangxi/index?gpid=" + id + "\" style='margin-right: 8px;'>详细 </a>",
        "<a href=\"javascript:applyGongSi('" + id + "')\" style='margin-right: 8px;'>修改 </a>",
        "<a href=\"javascript:deleteGongSi('" + id + "')\" style='color: red;'>删除 </a>"
    ].join('');
}

//回车搜索
document.onkeydown = function (e) {
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
        $("#queryButton").click();
    }
}


function applyGongSi(id) {
    $('#myModal').modal('show');
    if (typeof id == "undefined" || id == null || id == "") {
        //添加
        $('#myModalLabel').html("添加公司");
        $("#code").val("");
        $("#code").attr("readonly", false);
        $("#name").val("");
    } else {
        //修改
        var url = "http://127.0.0.1:8080/findGP?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                $('#myModalLabel').html("修改公司");
                $("#code").val(data.id);
                $("#name").val(data.name);
                $("#code").attr("readonly", true);
            }
        })
    }
}

function deleteGongSi(id) {
    Ewin.confirm({message: "确认删除"}).on(function (e) {
        if (!e) {
            return;
        }
        var url = "http://127.0.0.1:8080/deleteGP?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                fillTable()
            }
        })
    });
}
