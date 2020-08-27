<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="所属分类" prop="typeId">
      <template>
    	<el-select v-model="dataForm.typeId" placeholder="请选择">
    	  <el-option
    		v-for="item1 in typelist"
    		:key="item1.typeId"
    		:label="item1.typeName"
    		:value="item1.typeId">
    	  </el-option>
    	</el-select>
      </template>
    </el-form-item>
	<el-form-item label="标签名称" prop="labelName">
      <el-input v-model="dataForm.labelName" placeholder="标签名称"></el-input>
    </el-form-item>
    <el-form-item label="标签详情" prop="labelDetail">
      <el-input type="textarea" :rows="7" v-model="dataForm.labelDetail" placeholder="标签详情"></el-input>
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
          labelId: 0,
          labelName: '',
          labelDetail: '',
          remark: '',
		  typeId:''
        },
        dataRule: {
          labelName: [
            { required: true, message: '标签名称不能为空', trigger: 'blur' }
          ]
        },
		typelist:[]
      }
    },
    methods: {
      init (id) {
        this.dataForm.labelId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.labelId) {
            this.$http({
              url: this.$http.adornUrl(`/rep/labels/info/${this.dataForm.labelId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.labelName = data.labels.labelName
                this.dataForm.labelDetail = data.labels.labelDetail
                this.dataForm.remark = data.labels.remark
				this.dataForm.typeId = data.labels.typeId
              }
            })
          }
        })
		//获取标签分类列表
		this.$http({
		  url: this.$http.adornUrl('/rep/labelstype/list'),
		  method: 'get'
		}).then(({data}) => {
			this.typelist = data.page.list
		})
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/rep/labels/${!this.dataForm.labelId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'labelId': this.dataForm.labelId || undefined,
				'typeId': this.dataForm.typeId,
                'labelName': this.dataForm.labelName,
                'labelDetail': this.dataForm.labelDetail,
                'remark': this.dataForm.remark
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
