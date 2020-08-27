<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="报修单编号" prop="reporterOrder">
      <el-input v-model="dataForm.reporterOrder" placeholder="报修单编号"></el-input>
    </el-form-item>
    <el-form-item label="抢修单编号" prop="urgentOrder">
      <el-input v-model="dataForm.urgentOrder" placeholder="抢修单编号"></el-input>
    </el-form-item>
    <el-form-item label="抢修单状态 0-已归档 1-审核中 2-审核不通过 3-待派单 4-已派单 5-抢修中" prop="orderStatus">
      <el-input v-model="dataForm.orderStatus" placeholder="抢修单状态 0-已归档 1-审核中 2-审核不通过 3-待派单 4-已派单 5-抢修中"></el-input>
    </el-form-item>
    <el-form-item label="受理时间" prop="acceptTime">
      <el-input v-model="dataForm.acceptTime" placeholder="受理时间"></el-input>
    </el-form-item>
    <el-form-item label="派单时间" prop="dispatchTime">
      <el-input v-model="dataForm.dispatchTime" placeholder="派单时间"></el-input>
    </el-form-item>
    <el-form-item label="到达现场时间" prop="arrivedTime">
      <el-input v-model="dataForm.arrivedTime" placeholder="到达现场时间"></el-input>
    </el-form-item>
    <el-form-item label="修复时间 -分钟" prop="repairedTime">
      <el-input v-model="dataForm.repairedTime" placeholder="修复时间 -分钟"></el-input>
    </el-form-item>
    <el-form-item label="故障原因" prop="accidentReason">
      <el-input v-model="dataForm.accidentReason" placeholder="故障原因"></el-input>
    </el-form-item>
    <el-form-item label="报修内容" prop="repairedContent">
      <el-input v-model="dataForm.repairedContent" placeholder="报修内容"></el-input>
    </el-form-item>
    <el-form-item label="抢修工作评价" prop="evaluateContent">
      <el-input v-model="dataForm.evaluateContent" placeholder="抢修工作评价"></el-input>
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
          reporterOrder: '',
          urgentOrder: '',
          orderStatus: '',
          acceptTime: '',
          dispatchTime: '',
          arrivedTime: '',
          repairedTime: '',
          accidentReason: '',
          repairedContent: '',
          evaluateContent: '',
          remark: ''
        },
        dataRule: {
          reporterOrder: [
            { required: true, message: '报修单编号不能为空', trigger: 'blur' }
          ],
          urgentOrder: [
            { required: true, message: '抢修单编号不能为空', trigger: 'blur' }
          ],
          orderStatus: [
            { required: true, message: '抢修单状态 0-已归档 1-审核中 2-审核不通过 3-待派单 4-已派单 5-抢修中不能为空', trigger: 'blur' }
          ],
          acceptTime: [
            { required: true, message: '受理时间不能为空', trigger: 'blur' }
          ],
          dispatchTime: [
            { required: true, message: '派单时间不能为空', trigger: 'blur' }
          ],
          arrivedTime: [
            { required: true, message: '到达现场时间不能为空', trigger: 'blur' }
          ],
          repairedTime: [
            { required: true, message: '修复时间 -分钟不能为空', trigger: 'blur' }
          ],
          accidentReason: [
            { required: true, message: '故障原因不能为空', trigger: 'blur' }
          ],
          repairedContent: [
            { required: true, message: '报修内容不能为空', trigger: 'blur' }
          ],
          evaluateContent: [
            { required: true, message: '抢修工作评价不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/rep/urgentorder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.reporterOrder = data.urgentorder.reporterOrder
                this.dataForm.urgentOrder = data.urgentorder.urgentOrder
                this.dataForm.orderStatus = data.urgentorder.orderStatus
                this.dataForm.acceptTime = data.urgentorder.acceptTime
                this.dataForm.dispatchTime = data.urgentorder.dispatchTime
                this.dataForm.arrivedTime = data.urgentorder.arrivedTime
                this.dataForm.repairedTime = data.urgentorder.repairedTime
                this.dataForm.accidentReason = data.urgentorder.accidentReason
                this.dataForm.repairedContent = data.urgentorder.repairedContent
                this.dataForm.evaluateContent = data.urgentorder.evaluateContent
                this.dataForm.remark = data.urgentorder.remark
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
              url: this.$http.adornUrl(`/rep/urgentorder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'reporterOrder': this.dataForm.reporterOrder,
                'urgentOrder': this.dataForm.urgentOrder,
                'orderStatus': this.dataForm.orderStatus,
                'acceptTime': this.dataForm.acceptTime,
                'dispatchTime': this.dataForm.dispatchTime,
                'arrivedTime': this.dataForm.arrivedTime,
                'repairedTime': this.dataForm.repairedTime,
                'accidentReason': this.dataForm.accidentReason,
                'repairedContent': this.dataForm.repairedContent,
                'evaluateContent': this.dataForm.evaluateContent,
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
