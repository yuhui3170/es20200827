<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="资源id" prop="resourceId">
      <el-input v-model="dataForm.resourceId" placeholder="资源id"></el-input>
    </el-form-item>
    <el-form-item label="仓库名称" prop="storeName">
      <el-input v-model="dataForm.storeName" placeholder="仓库名称"></el-input>
    </el-form-item>
    <el-form-item label="仓库负责人" prop="storeManager">
      <el-input v-model="dataForm.storeManager" placeholder="仓库负责人"></el-input>
    </el-form-item>
    <el-form-item label="仓库联系电话" prop="managerPhone">
      <el-input v-model="dataForm.managerPhone" placeholder="仓库联系电话"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
          storeId: 0,
          resourceId: '',
          storeName: '',
          storeManager: '',
          managerPhone: '',
          remark: ''
        },
        dataRule: {
          resourceId: [
            { required: true, message: '资源id不能为空', trigger: 'blur' }
          ],
          storeName: [
            { required: true, message: '仓库名称不能为空', trigger: 'blur' }
          ],
          storeManager: [
            { required: true, message: '仓库负责人不能为空', trigger: 'blur' }
          ],
          managerPhone: [
            { required: true, message: '仓库联系电话不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.storeId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.storeId) {
            this.$http({
              url: this.$http.adornUrl(`/dep/store/info/${this.dataForm.storeId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.resourceId = data.store.resourceId
                this.dataForm.storeName = data.store.storeName
                this.dataForm.storeManager = data.store.storeManager
                this.dataForm.managerPhone = data.store.managerPhone
                this.dataForm.remark = data.store.remark
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
              url: this.$http.adornUrl(`/dep/store/${!this.dataForm.storeId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'storeId': this.dataForm.storeId || undefined,
                'resourceId': this.dataForm.resourceId,
                'storeName': this.dataForm.storeName,
                'storeManager': this.dataForm.storeManager,
                'managerPhone': this.dataForm.managerPhone,
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
