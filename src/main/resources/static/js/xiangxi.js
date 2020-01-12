$(function () {
    findGongSi();
    fillTable();
    $('#datetimepicker2').datetimepicker({
        format: 'YYYY-MM-DD HH:mm',
        locale: moment.locale('zh-cn')
    });
});

var urlPath = window.document.location.href;
var docPath = window.document.location.pathname;
var index = urlPath.indexOf(docPath);
var serverPath = urlPath.substring(0, index);

var baseUrl = serverPath+'/pageQueryXX';


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
                size: params.limit   //页面大小
            };
        },
        columns: [
            // {checkbox: true},
            {field: 'time', title: '时间'},
            {field: 'tougu', title: '投股'},
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
        "<a href=\"javascript:applyXiangXi('" + id + "')\" style='margin-right: 8px;'>修改 </a>",
        "<a href=\"javascript:deleteXiangXi('" + id + "')\" style='color: red;'>删除 </a>"
    ].join('');
}
/**
 * 填充表格
 */
function fillTable1() {
    var oTable = $("#tb1");
    oTable.empty();
    var queryUrl = serverPath+"/queryXX";
    $.ajax({
        url: queryUrl,
        type: 'get',
        data: {},
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#parentId").val(data[i].gongsi_id);
                $("#parentId1").val(data[i].gongsi_id);
                var gongsi_id = data[i].gongsi_id;
                if ("-" == gongsi_id) {
                    oTable.append("<tr class='" + getFlag(i) + "'><td>错误数据，需返回后重新载入</td></tr>");
                    return;
                }

                if ("-" != data[i].time) {//当没有数据时不进行渲染,为了获取父id仍要进行填充数据
                    oTable.append("<tr class='" + getFlag(i) + "'><td>" + data[i].time + "</td><td>" + data[i].tougu + "</td></td><td>" + render(data[i].id) + "</td></tr>");
                }
            }
        }
    })
}

function render(id) {
    var str = "<a href=\"javascript:applyXiangXi('" + id + "')\" style='margin-right: 8px;'>修改 </a>";
    str = str + "<a href=\"javascript:deleteXiangXi('" + id + "')\" style='color: red;'>删除 </a>";
    return str;
}


function applyXiangXi(id) {
    if (typeof id == "undefined" || id == null || id == "") {
            $('#myModal').modal('show');
            //添加
            $('#myModalLabel').html("添加详细");
            $("#tougu").val("");
            $("#id").val("");
            $("#time").val("");
    } else {
        //修改
        $('#myModal').modal('show');
        var url = serverPath+"/findXX?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                $('#myModalLabel').html("修改详细");
                $("#time").val(data.time);
                $("#tougu").val(data.tougu);
                $("#id").val(data.id);
            }
        })
    }
}


/**
 * 构造表格行的样式
 * @param i
 * @returns {number}
 */
function getFlag(i) {
    var flag = i % 4;
    if (flag == 0) {
        flag = "active"
    } else if (flag == 1) {
        flag = "success"
    } else if (flag == 2) {
        flag = "warning"
    } else if (flag == 3) {
        flag = "danger"
    }
    return flag;
}

function goBack() {
    window.location.href = serverPath+"/index";
}


function deleteXiangXi(id) {
    Ewin.confirm({message: "确认删除"}).on(function (e) {
        if (!e) {
            return;
        }
        var url = serverPath+"/deleteXX?id=" + id;
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

/**
 * 查询标题的公司信息
 */
function findGongSi() {
    var url = serverPath+"/findGP";
    $.ajax({
        url: url,
        type: 'get',
        data: {},
        success: function (data) {
            $("#gongsi_code").empty();
            $("#gongsi_name").empty();
            $("#gongsi_code").append(data.id);
            $("#gongsi_name").append(data.name);
            return data;
        }
    })
}

