<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名称" prop="resourceName">
      <el-input v-model="dataForm.resourceName" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="resourceAddr">
      <el-input v-model="dataForm.resourceAddr" placeholder="地址"></el-input>
    </el-form-item>
    <el-form-item label="联系号码" prop="resourcePhone">
      <el-input v-model="dataForm.resourcePhone" placeholder="联系号码"></el-input>
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
          resourceId: 0,
          resourceName: '',
          resourceAddr: '',
          resourcePhone: '',
          remark: ''
        },
        dataRule: {
          resourceName: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          resourceAddr: [
            { required: true, message: '地址不能为空', trigger: 'blur' }
          ],
          resourcePhone: [
            { required: true, message: '联系号码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.resourceId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.resourceId) {
            this.$http({
              url: this.$http.adornUrl(`/dep/resource/info/${this.dataForm.resourceId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.resourceName = data.resource.resourceName
                this.dataForm.resourceAddr = data.resource.resourceAddr
                this.dataForm.resourcePhone = data.resource.resourcePhone
                this.dataForm.remark = data.resource.remark
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
              url: this.$http.adornUrl(`/dep/resource/${!this.dataForm.resourceId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'resourceId': this.dataForm.resourceId || undefined,
                'resourceName': this.dataForm.resourceName,
                'resourceAddr': this.dataForm.resourceAddr,
                'resourcePhone': this.dataForm.resourcePhone,
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
