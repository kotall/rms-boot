/**
 * 商品基本信息表js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {height: $(window).height()-54});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url: '../../litemall/goods/list?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.name = vm.keyword;
			return params;
		},
		columns: [
			{checkbox: true},
			{field : "storeId", title : "店铺ID", width : "100px"}, 
			{field : "goodsSn", title : "商品编号", width : "100px"}, 
			{field : "name", title : "商品名称", width : "100px"}, 
			{field : "categoryId", title : "商品所属类目ID", width : "100px"}, 
			{field : "brandId", title : "所属品牌商", width : "100px"},
			/*{field : "gallery", title : "商品宣传图片列表，采用JSON数组格式", width : "100px"}, */
			{field : "keywords", title : "商品关键字", width : "100px"},
			{field : "brief", title : "商品简介", width : "100px"},
            {field : "picUrl", title : "商品图片", width : "100px",
                formatter : function(value, row, index) {
                    return '<img  src="'+value+'" class="img-rounded" width="80px" height="60px">';
                }},
			{field : "isOnSale", title : "是否上架", width : "100px",
                formatter : function(value, row, index) {
                    if (value === true){
                        return '<span class="label label-success">是</span>';
					}
                    return '<span class="label label-warning">否</span>';
                }},
			/*{field : "shareUrl", title : "商品分享朋友圈图片", width : "100px"}, */
			{field : "isNew", title : "是否新品首发", width : "100px",
                formatter : function(value, row, index) {
                    if (value === 0){
                        return '<span class="label label-success">是</span>';
                    }
                    return '<span class="label label-warning">否</span>';
                }},
			{field : "isHot", title : "是否人气推荐", width : "100px",
                formatter : function(value, row, index) {
                    if (value === 0){
                        return '<span class="label label-success">是</span>';
                    }
                    return '<span class="label label-warning">否</span>';
                }},
			{field : "unit", title : "商品单位", width : "100px"},
			{field : "counterPrice", title : "专柜价格", width : "100px"}, 
			{field : "retailPrice", title : "零售价格", width : "100px"}, 
			/*{field : "detail", title : "商品详细介绍", width : "100px",
				formatter : function(value, row, index) {
					return $.base64.decode(value);
				}
			},*/
			{field : "id", title : "操作", width : "100px",
				formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('litemall:goods:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.id+'\')" title="编辑"><i class="fa fa-pencil"></i></a>\t';
                    }
                    if (hasPermission('litemall:goods:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.remove(\''+row.id+'\')" title="删除"><i class="fa fa-trash-o"></i></a>\t';
                    }
                    return _html;				}
			}
            /*,{field : "addTime", title : "创建时间", width : "100px"},
            {field : "updateTime", title : "更新时间", width : "100px"},
            {field : "deleted", title : "逻辑删除", width : "100px"}*/
		]
	})
}

/**
 * 商品规格列表
 */
function getGrid2(id) {
    $('#dataGrid2').bootstrapTableEx({
        url: '../../litemall/specification/getByGoodsId?_' + $.now()+'&goodsId=' + id,
        height: $(window).height - 200,
        pagination:false,
        dataField: "rows",
        queryParams: function(params){
            params.name = vm.keyword;
            return params;
        },
        columns: [
            {checkbox: true},
            {field : "specification", title : "规格名", width : "100px"},
            {field : "value", title : "规格值", width : "100px"},
            {field : "picUrl", title : "规格图片", width : "100px",
                formatter : function(value, row, index) {
                    return '<img  src="'+value+'" class="img-rounded" width="80px" height="60px">';
            }},
            {field : "id", title : "操作", width : "100px",
                formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('litemall:goods:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.specificationEdit(\''+index+'\')" title="编辑"><i class="fa fa-pencil"></i></a>\t';
                    }
                    if (hasPermission('litemall:goods:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.specificationDel(\''+row.specification+'\')" title="删除"><i class="fa fa-trash-o"></i></a>\t';
                    }
                    return _html;				}
            }
        ]
    })
}

/**
 * 商品库存
 */
function getGrid3(id) {
    $('#dataGrid3').bootstrapTableEx({
        url: '../../litemall/goodsproduct/getByGoodsId?_' + $.now()+'&goodsId=' + id,
        height: $(window).height - 200,
        pagination:false,
        dataField: "rows",
        queryParams: function(params){
            params.name = vm.keyword;
            return params;
        },
        columns: [
            {checkbox: true},
            {field : "specifications", title : "规格名", width : "100px"},
            {field : "price", title : "货品售价", width : "100px"},
            {field : "number", title : "货品数量", width : "100px"},
            /*{field : "picUrl", title : "货品图片", width : "100px",
                formatter : function(value, row, index) {
                    return '<img  src="'+value+'" class="img-rounded" width="80px" height="60px">';
                }},*/
            {field : "id", title : "操作", width : "100px",
                formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('litemall:goods:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.productEdit(\''+index+'\')" title="设置"><i class="fa fa-pencil"></i></a>\t';
                    }

                    return _html;				}
            }
        ]
    })
}

/**
 * 商品参数
 */
function getGrid4(id) {
    $('#dataGrid4').bootstrapTableEx({
        url: '../../litemall/attribute/getByGoodsId?_' + $.now()+'&goodsId=' + id,
        height: $(window).height - 200,
        pagination:false,
        dataField: "rows",
        queryParams: function(params){
            params.name = vm.keyword;
            return params;
        },
        columns: [
            {checkbox: true},
            {field : "attribute", title : "商品参数名称", width : "100px"},
            {field : "value", title : "商品参数值", width : "100px"},
            {field : "id", title : "操作", width : "100px",
                formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('litemall:goods:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.attributeEdit(\''+index+'\')" title="编辑"><i class="fa fa-pencil"></i></a>\t';
                    }
                    if (hasPermission('litemall:goods:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.attributeDel(\''+row.attribute+'\')" title="删除"><i class="fa fa-trash-o"></i></a>\t';
                    }
                    return _html;			}
            }
        ]
    })
}

var vm = new Vue({
	el:'#dpLTE',
	data: {
		keyword: null,
        showList: true,
        isAdd:true,
        editIndex:null,
        title:'',
        gallerys:["","","","",""],
        categoryDatas:[],
        storeList:[],
        liteMallGoods: {
            id: null,
            picUrl:'',
            gallery:'',
            detail:'',
            categoryId:'',
            liteMallGoodsSpecification:{
                specification:'1',
                value:'2',
                picUrl:''
            },
            liteMallGoodsProduct:{
                specification:'',
                price:'',
                number:''
            },
            liteMallGoodsAttribute:{
                attribute:'',
                value:''
            }
        }
	},
	methods : {
		load: function() {
			vm.showList=true;
			vm.liteMallGoods = {};
			vm.gallerys = [];
            $('.add').remove();
            reload();
			//vm.categoryDatas = [];
            editorUtils.clear(editor);
			$('#dataGrid').bootstrapTable('refresh');
            $("#dataGrid2").bootstrapTable('destroy');
            $("#dataGrid3").bootstrapTable('destroy');
            $("#dataGrid4").bootstrapTable('destroy');

		},
        add: function(){
            vm.showList = false;
            getGrid2();
            getGrid3();
            getGrid4();
        },
		edit: function(id) {
            getGrid2(id)
            getGrid3(id)
            getGrid4(id)
            vm.showList = false;
            vm.getCategory();
            $.SetForm({
                url: '../../litemall/goods/info?_' + $.now(),
                param: id,
                success: function(data) {
                    debugger
                    vm.liteMallGoods = data;
                    $('#select2-id').val(vm.liteMallGoods.categoryId).trigger("change");
                    var gallery = vm.liteMallGoods.gallery;
                    var _gallery = gallery.split(";");
                    for(i=0;i<_gallery.length;i++){
                        vm.gallerys[i]=_gallery[i];
                    }
                    editor = editorUtils.init({
                        change: function (html) {
                            debugger
                            html = $.base64.encode(html);
                            vm.liteMallGoods.detail=html;
                        }
                    });
                    editor.txt.html($.base64.decode(vm.liteMallGoods.detail));
                }
            });
		},
        remove: function(menuId) {
            var ids = [];
                ids.push(menuId);
				$.RemoveForm({
					url: '../../litemall/goods/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			},
        getCategory:function () {
            var _self = this;
            $.ajax("../../litemall/category/getParentCategory",{}).then(function(response){
                _self.categoryDatas = response.rows;
            });
        },
        specificationSubmit:function () {
            var datas = {
                specification:$("#specification").val(),
                value:$("#value").val(),
                picUrl:$("#picUrl2").val()
            }
            $("#myModal").modal('hide');
            if(vm.isAdd){
                $('#dataGrid2').bootstrapTable('insertRow', {
                    index:$('#dataGrid2').bootstrapTable('getOptions').totalRows,
                    row:datas
                });
                $('#dataGrid3').bootstrapTable('insertRow', {
                    index:$('#dataGrid3').bootstrapTable('getOptions').totalRows,
                    row:{specifications:$("#specification").val()}
                });
            }else{
                $('#dataGrid2').bootstrapTable('updateRow', {
                    index:vm.editIndex,
                    row:datas
                });
                $('#dataGrid3').bootstrapTable('updateRow', {
                    index:vm.editIndex,
                    row:{specification:$("#specification").val()}
                });
            }
        },
        specificationEdit:function (index) {
            var row = $("#dataGrid2").bootstrapTable('getData')[index];
            $("#specification").val(row.specification);
            $("#value").val(row.value);
            $("#picUrl2").val(row.picUrl);
            $("#demo3").attr("src",row.picUrl);
            vm.isAdd = false;
            vm.editIndex = index;
            $("#myModal").modal('show');
        },
        specificationDel:function (specification) {
            debugger
            $('#dataGrid2').bootstrapTable('remove', {
                field: "specification",
                values: [specification]
            });
            $('#dataGrid3').bootstrapTable('remove', {
                field: "specifications",
                values: [specification]
            });
        },
        productEdit:function (index) {
            var row = $("#dataGrid3").bootstrapTable('getData')[index];
            vm.editIndex = index;
            $("#specification3").val(row.specifications);
            $("#price").val(row.price);
            $("#number").val(row.number);
            $("#productModal").modal('show');
        },
        productSubmit:function () {
            debugger
            var datas = {
                specifications:$("#specification3").val(),
                price:$("#price").val(),
                number:$("#number").val()
            }
            $("#productModal").modal('hide');
            $('#dataGrid3').bootstrapTable('updateRow', {
                index:vm.editIndex,
                row:datas
            });
        },
        attributeSubmit:function () {
            var datas = {
                attribute:$("#attribute").val(),
                value:$("#value4").val()
            }
            $("#attributeModal").modal('hide');
            if(vm.isAdd){
                $('#dataGrid4').bootstrapTable('insertRow', {
                    index:$('#dataGrid2').bootstrapTable('getOptions').totalRows,
                    row:datas
                });
            }else{
                $('#dataGrid4').bootstrapTable('updateRow', {
                    index:vm.editIndex,
                    row:datas
                });
            }
        },
        attributeEdit:function (index) {
            var row = $("#dataGrid4").bootstrapTable('getData')[index];
            vm.editIndex = index;
            vm.isAdd = false;
            $("#attribute").val(row.attribute);
            $("#attribute").attr({"readonly":"readonly"});
            $("#value4").val(row.value);
            $("#attributeModal").modal('show');
        },
        attributeDel:function (attribute) {
            $('#dataGrid4').bootstrapTable('remove', {
                field: "attribute",
                values: [attribute]
            });
        },
        getStoreList: function () {
            var _self = this;
            $.ajax({
                'url': "../../litemall/store/list?_" + $.now(),
                'type': 'POST',
                'contentType': 'application/json',
                'data': JSON.stringify({'pageNumber': 1, 'pageSize' : 10}),
                'dataType': 'json'
            }).then(function(res){
                _self.storeList = res.rows;
            });
        },
        saveOrUpdate: function () {
            if (!$('#form').Validform()) {
                return false;
            }
            debugger
            vm.liteMallGoods.liteMallGoodsSpecification = $('#dataGrid2').bootstrapTable('getData');
            vm.liteMallGoods.liteMallGoodsProduct = $('#dataGrid3').bootstrapTable('getData');
            vm.liteMallGoods.liteMallGoodsAttribute = $('#dataGrid4').bootstrapTable('getData');
            var url = vm.liteMallGoods.id == null ? '../../litemall/goods/save?_' + $.now() : '../../litemall/goods/update?_' + $.now();
            debugger
            if(vm.liteMallGoods.id == null){
                $.SaveForm({
                    url: url,
                    param: vm.liteMallGoods,
                    success: function(data) {
                        $.currentIframe().vm.load();
                    }
                });
            }else{
                $.ConfirmForm({
                    url: url,
                    param: vm.liteMallGoods,
                    success: function(data) {
                        $.currentIframe().vm.load();
                    }
                });
            }


        }
	},
    created:function () {
        this.getCategory();
        this.getStoreList();
    }
})


/**
 * 监听model隐藏事件
 */
$("body").on("hidden.bs.modal", function() {
    vm.isAdd = true;
    $("#specification").val(null);
    $("#value").val(null);
    $("#picUrl2").val(null);
    $('#demo3').attr('src', "");

    $("#value4").val(null);
    $("#attribute").val(null);
});

$("#select2-id").select2({
    placeholder: "请选择商品类目",
    allowClear: true
}).on('change', function (e) {
    debugger
    vm.liteMallGoods.categoryId=$("#select2-id").val();
});

/**
 * 富文本编辑器初始化
 */
editor = editorUtils.init({
    change: function (html) {
        debugger
        html = $.base64.encode(html);
        vm.liteMallGoods.detail=html;
    }
});

/**
 * 图片上传
 */
layui.use('upload', function(){
    var $ = layui.jquery
        ,upload = layui.upload;
    //规格图片上传
    var uploadInst3 = upload.render({
        elem: '#demo3'
        ,url: '../../litemall/storage/create'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo3').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            var _self = this;
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            debugger
            var url = res.rows.url;
            $("#picUrl2").val(url);
            //上传成功
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText3');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
});


function picUrlDeleteImgOption(obj) {
    ImageUpload.deleteImg(obj);
    vm.liteMallGoods.picUrl="";
}

function picUrlChangeOption(obj) {
    var url = ImageUpload.change(obj);
    debugger
    vm.liteMallGoods.picUrl=url;
}


function galleryDeleteImgOption(obj,index) {
    ImageUpload.deleteImg(obj);
    vm.gallerys[index]="";
}

function galleryChangeOption(obj,index) {
    var url = ImageUpload.change(obj);
    vm.gallerys[index]=url;
    vm.liteMallGoods.gallery=vm.gallerys.join(";");
}





