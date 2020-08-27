<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="分类名称" prop="typeName">
      <el-input v-model="dataForm.typeName" placeholder="分类名称"></el-input>
    </el-form-item>
	<el-form-item label="描述" prop="typeRemark">
	  <el-input type="textarea" :rows="7" v-model="dataForm.typeRemark" placeholder="描述"></el-input>
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
          typeId: 0,
          typeName: '',
          typeRemark: ''
        },
        dataRule: {
          typeName: [
            { required: true, message: '分类名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.typeId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.typeId) {
            this.$http({
              url: this.$http.adornUrl(`/dep/equipmentstype/info/${this.dataForm.typeId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.typeName = data.equipmentsType.typeName
                this.dataForm.typeRemark = data.equipmentsType.typeRemark
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/dep/equipmentstype/${!this.dataForm.typeId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'typeId': this.dataForm.typeId || undefined,
                'typeName': this.dataForm.typeName,
                'typeRemark': this.dataForm.typeRemark
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
