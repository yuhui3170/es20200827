<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="真实姓名" prop="workerName">
      <el-input v-model="dataForm.workerName" placeholder="真实姓名"></el-input>
    </el-form-item>
    <el-form-item label="用户名" prop="workNickname">
      <el-input v-model="dataForm.workNickname" placeholder="英文字母、数字组合，以英文字母开头，限4-16个字符"></el-input>
    </el-form-item>
    <el-form-item label="联系号码" prop="workMobile">
      <el-input v-model="dataForm.workMobile" placeholder="联系号码"></el-input>
    </el-form-item>
    <el-form-item label="出生日期" prop="workerBirth">
      <el-date-picker
        v-model="dataForm.workerBirth"
        type="date"
        placeholder="选择日期"
        value-format="yyyy-MM-dd">
      </el-date-picker>
    </el-form-item>
	<el-form-item label="供电所" prop="resourceId">
	  <template>
	    <el-select v-model="dataForm.resourceId" placeholder="请选择">
	      <el-option
	        v-for="item in resourcelist"
	        :key="item.resourceId"
	        :label="item.resourceName"
	        :value="item.resourceId">
	      </el-option>
	    </el-select>
	  </template>
	</el-form-item>
    <el-form-item label="职位" prop="workerPosition">
      <el-input v-model="dataForm.workerPosition" placeholder="职位"></el-input>
    </el-form-item>
    <el-form-item label="头像" prop="workPic">
	  <el-upload
	    class="upload-demo"
		ref="upload"
	    :action="actionsUrl"
		:file-list="file_list"
	    :on-preview="handlePreview"
	    :on-remove="handleRemove"
	    :before-remove="beforeRemove"
		:on-success="uploadSuccess"
	    multiple
	    :limit="1"
	    :on-exceed="handleExceed">
	    <el-button size="small" type="primary">点击上传</el-button>
	    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
	  </el-upload>
	  <img :src="dataForm.picUrl" style="width:146px;height:146px;border-radius:3%;" v-if="dataForm.picUrl!=null"/>
    </el-form-item>
    <el-form-item label="接单数量" prop="ordersReceived">
      <el-input v-model="dataForm.ordersReceived" placeholder="接单数量" :readonly="true"></el-input>
    </el-form-item>
    <el-form-item label="结单数量" prop="ordersFinished">
      <el-input v-model="dataForm.ordersFinished" placeholder="结单数量" :readonly="true"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input type="textarea" :rows="7" v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          workerId: 0,
          resourceId: null,
          workerName: null,
          workerBirth: null,
          workerPosition: null,
          ordersReceived: 0,
          ordersFinished: 0,
          remark: null,
          workPic: null,
          workNickname: null,
          workMobile: null,
		  picUrl:null
        },
		file_list:[],
        dataRule: {
          workerName: [
            { required: true, message: '真实姓名不能为空', trigger: 'blur' }
          ],
          workNickname: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          workMobile: [
            { required: true, message: '联系号码不能为空', trigger: 'blur' }
          ]
        },
		actionsUrl:this.$http.adornUrl('/dep/file/picture/upload'),
		resourcelist: []
      }
    },
    methods: {
      init (id) {
        this.dataForm.workerId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.workerId) {
            this.$http({
              url: this.$http.adornUrl(`/dep/workers/info/${this.dataForm.workerId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.resourceId = data.workers.resourceId
                this.dataForm.workerName = data.workers.workerName
                this.dataForm.workerBirth = data.workers.workerBirth
                this.dataForm.workerPosition = data.workers.workerPosition
                this.dataForm.ordersReceived = data.workers.ordersReceived
                this.dataForm.ordersFinished = data.workers.ordersFinished
                this.dataForm.remark = data.workers.remark
                this.dataForm.workPic = data.workers.workPic
				console.log(this.dataForm.workPic);
				if(this.dataForm.workPic!=null){
					this.dataForm.picUrl = this.$http.adornUrl('/dep/file/picture/download?path='+encodeURIComponent(this.dataForm.workPic));
					console.log(this.dataForm.picUrl);
				}
                this.dataForm.workNickname = data.workers.workNickname
                this.dataForm.workMobile = data.workers.workMobile
              }
            })
          }else{
			  this.file_list=[]
			  this.dataForm.picUrl = null;
		  }
        })
		//获取供电所列表
		this.$http({
		  url: this.$http.adornUrl('/dep/resource/list'),
		  method: 'get'
		}).then(({data}) => {
		  if (data && data.code === 0) {
		    this.resourcelist = data.page.list
		  } else {
		    this.resourcelist = []
		  }
		})
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/dep/workers/${!this.dataForm.workerId ? 'save_limit' : 'update_limit'}`),
              method: 'post',
              data: this.$http.adornData({
                'workerId': this.dataForm.workerId || undefined,
                'resourceId': this.dataForm.resourceId,
                'workerName': this.dataForm.workerName,
                'workerBirth': this.dataForm.workerBirth,
                'workerPosition': this.dataForm.workerPosition,
                'ordersReceived': this.dataForm.ordersReceived,
                'ordersFinished': this.dataForm.ordersFinished,
                'remark': this.dataForm.remark,
                'workPic': this.dataForm.workPic,
                'workNickname': this.dataForm.workNickname,
                'workMobile': this.dataForm.workMobile
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
				  if(data.data.status=='0'){
					  this.$message({
						message: '操作成功',
						type: 'success',
						duration: 1500,
						onClose: () => {
						  this.visible = false
						  this.$emit('refreshDataList')
						  return;
						}
					  })
				  }else{
					  this.$message({
						message: data.data.status=='1'?'手机号已存在':'用户名已存在',
						type: 'none',
						duration: 1500
					  })
					  return;
				  }
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      },
	  uploadSuccess:function(response, file, fileList) {
		  console.log("上传文件成功response" +response);
		  //上传文件成功responseE:\images\15972215137861.jpg
		  console.log("上传文件成功file" +file);
		  console.log("上传文件成功fileList" +fileList);
		  //保存图片地址至用户头像
		  this.dataForm.workPic = response;
		  if(this.dataForm.workPic!=null){
			  this.dataForm.picUrl = this.$http.adornUrl('/dep/file/picture/download?path='+encodeURIComponent(this.dataForm.workPic));
				console.log('this.dataForm.picUrl:'+this.dataForm.picUrl);
				this.$refs.upload.clearFiles();
		  }
	  },
	  uploadError:function (response, file, fileList) {

		  console.log("上传文件失败response" +response);
		  console.log("上传文件失败file" +file);
		  console.log("上传文件失败fileList" +fileList);
	  },
	  handleRemove(file, fileList) {
		  console.log("handleRemove");
	          console.log(file, fileList);
			  this.dataForm.workPic = null;
			  this.dataForm.picUrl = null;
	        },
	        handlePreview(file) {
				console.log("handlePreview");
	          console.log(file);
	        },
	        handleExceed(files, fileList) {
	          //this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
	        },
	        beforeRemove(file, fileList) {
	          return this.$confirm(`确定移除 ${ file.name }？`);
	        }
    }
  }
</script>
