/**
 * 新增-类目表js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		liteMallCategory: {
			id: 0,
			pid:'',
            level:'L1'
		},
        categoryDatas:[]
	},
    wathc:{
        arr: function() {
            this.$nextTick(function(){
                /*现在数据已经渲染完毕*/
            })
        }
    },
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../litemall/category/save?_' + $.now(),
		    	param: vm.liteMallCategory,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		},
        getCategory:function () {
            var _self = this;
            $.ajax("../../litemall/category/getSecondCategory").then(function(response){
                _self.categoryDatas = response.rows;
            });
            /*this.$http({           //调用接口
                method:'GET',
                url:"/rms/litemall/category/getSecondCategory"  //this指data
            }).then(function(response){  //接口返回数据
                this.categoryDatas = response.data;
				console.log(response);
            },function(error){
            })*/

        }
	},
	created: function () {
		this.getCategory();
    }
});

layui.use('upload', function(){
    var $ = layui.jquery
        ,upload = layui.upload;

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        ,url: '../..//litemall/storage/create'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
});


