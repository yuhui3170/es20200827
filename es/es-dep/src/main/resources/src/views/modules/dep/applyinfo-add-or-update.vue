<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="设备id" prop="equipId">
      <el-input v-model="dataForm.equipId" placeholder="设备id"></el-input>
    </el-form-item>
    <el-form-item label="申请时间" prop="applyTime">
      <el-input v-model="dataForm.applyTime" placeholder="申请时间"></el-input>
    </el-form-item>
    <el-form-item label="工人id" prop="workerId">
      <el-input v-model="dataForm.workerId" placeholder="工人id"></el-input>
    </el-form-item>
    <el-form-item label="工人名称" prop="workerName">
      <el-input v-model="dataForm.workerName" placeholder="工人名称"></el-input>
    </el-form-item>
    <el-form-item label="抢修单编号" prop="repairOrderid">
      <el-input v-model="dataForm.repairOrderid" placeholder="抢修单编号"></el-input>
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
          id: 0,
          equipId: '',
          applyTime: '',
          workerId: '',
          workerName: '',
          repairOrderid: '',
          remark: ''
        },
        dataRule: {
          equipId: [
            { required: true, message: '设备id不能为空', trigger: 'blur' }
          ],
          applyTime: [
            { required: true, message: '申请时间不能为空', trigger: 'blur' }
          ],
          workerId: [
            { required: true, message: '工人id不能为空', trigger: 'blur' }
          ],
          workerName: [
            { required: true, message: '工人名称不能为空', trigger: 'blur' }
          ],
          repairOrderid: [
            { required: true, message: '抢修单编号不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/dep/applyinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.equipId = data.applyinfo.equipId
                this.dataForm.applyTime = data.applyinfo.applyTime
                this.dataForm.workerId = data.applyinfo.workerId
                this.dataForm.workerName = data.applyinfo.workerName
                this.dataForm.repairOrderid = data.applyinfo.repairOrderid
                this.dataForm.remark = data.applyinfo.remark
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
              url: this.$http.adornUrl(`/dep/applyinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'equipId': this.dataForm.equipId,
                'applyTime': this.dataForm.applyTime,
                'workerId': this.dataForm.workerId,
                'workerName': this.dataForm.workerName,
                'repairOrderid': this.dataForm.repairOrderid,
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
