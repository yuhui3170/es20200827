<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="资源id" prop="resourceId">
      <el-input v-model="dataForm.resourceId" placeholder="资源id"></el-input>
    </el-form-item>
    <el-form-item label="工人姓名" prop="workerName">
      <el-input v-model="dataForm.workerName" placeholder="工人姓名"></el-input>
    </el-form-item>
    <el-form-item label="工人年龄" prop="workerAge">
      <el-input v-model="dataForm.workerAge" placeholder="工人年龄"></el-input>
    </el-form-item>
    <el-form-item label="工人职位" prop="workerPosition">
      <el-input v-model="dataForm.workerPosition" placeholder="工人职位"></el-input>
    </el-form-item>
    <el-form-item label="接单数量" prop="ordersReceived">
      <el-input v-model="dataForm.ordersReceived" placeholder="接单数量"></el-input>
    </el-form-item>
    <el-form-item label="结单数量" prop="ordersFinished">
      <el-input v-model="dataForm.ordersFinished" placeholder="结单数量"></el-input>
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
          workerId: 0,
          resourceId: '',
          workerName: '',
          workerAge: '',
          workerPosition: '',
          ordersReceived: '',
          ordersFinished: '',
          remark: ''
        },
        dataRule: {
          resourceId: [
            { required: true, message: '资源id不能为空', trigger: 'blur' }
          ],
          workerName: [
            { required: true, message: '工人姓名不能为空', trigger: 'blur' }
          ],
          workerAge: [
            { required: true, message: '工人年龄不能为空', trigger: 'blur' }
          ],
          workerPosition: [
            { required: true, message: '工人职位不能为空', trigger: 'blur' }
          ],
          ordersReceived: [
            { required: true, message: '接单数量不能为空', trigger: 'blur' }
          ],
          ordersFinished: [
            { required: true, message: '结单数量不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ]
        }
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
                this.dataForm.workerAge = data.workers.workerAge
                this.dataForm.workerPosition = data.workers.workerPosition
                this.dataForm.ordersReceived = data.workers.ordersReceived
                this.dataForm.ordersFinished = data.workers.ordersFinished
                this.dataForm.remark = data.workers.remark
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
              url: this.$http.adornUrl(`/dep/workers/${!this.dataForm.workerId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'workerId': this.dataForm.workerId || undefined,
                'resourceId': this.dataForm.resourceId,
                'workerName': this.dataForm.workerName,
                'workerAge': this.dataForm.workerAge,
                'workerPosition': this.dataForm.workerPosition,
                'ordersReceived': this.dataForm.ordersReceived,
                'ordersFinished': this.dataForm.ordersFinished,
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
