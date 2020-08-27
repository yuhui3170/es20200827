<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="报修单编号" prop="reporterOrder">
      <el-input v-model="dataForm.reporterOrder" placeholder="报修单编号"></el-input>
    </el-form-item>
    <el-form-item label="报修人姓名" prop="reporterName">
      <el-input v-model="dataForm.reporterName" placeholder="报修人姓名"></el-input>
    </el-form-item>
    <el-form-item label="报修人联系方式" prop="reporterPhone">
      <el-input v-model="dataForm.reporterPhone" placeholder="报修人联系方式"></el-input>
    </el-form-item>
    <el-form-item label="报修时间" prop="reporterTime">
      <el-input v-model="dataForm.reporterTime" placeholder="报修时间"></el-input>
    </el-form-item>
    <el-form-item label="报修地点" prop="reporterAddr">
      <el-input v-model="dataForm.reporterAddr" placeholder="报修地点"></el-input>
    </el-form-item>
    <el-form-item label="事故发生地点" prop="accidentAddr">
      <el-input v-model="dataForm.accidentAddr" placeholder="事故发生地点"></el-input>
    </el-form-item>
    <el-form-item label="事故问题标签" prop="accidentLabel">
      <el-input v-model="dataForm.accidentLabel" placeholder="事故问题标签"></el-input>
    </el-form-item>
    <el-form-item label="事故描述内容" prop="accidentContent">
      <el-input v-model="dataForm.accidentContent" placeholder="事故描述内容"></el-input>
    </el-form-item>
    <el-form-item label="事故参考图片" prop="accidentPic">
      <el-input v-model="dataForm.accidentPic" placeholder="事故参考图片"></el-input>
    </el-form-item>
    <el-form-item label="智能推荐抢修资源单位" prop="resourceRecommend">
      <el-input v-model="dataForm.resourceRecommend" placeholder="智能推荐抢修资源单位"></el-input>
    </el-form-item>
    <el-form-item label="报修单备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="报修单备注"></el-input>
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
          reporterName: '',
          reporterPhone: '',
          reporterTime: '',
          reporterAddr: '',
          accidentAddr: '',
          accidentLabel: '',
          accidentContent: '',
          accidentPic: '',
          resourceRecommend: '',
          remark: ''
        },
        dataRule: {
          reporterOrder: [
            { required: true, message: '报修单编号不能为空', trigger: 'blur' }
          ],
          reporterName: [
            { required: true, message: '报修人姓名不能为空', trigger: 'blur' }
          ],
          reporterPhone: [
            { required: true, message: '报修人联系方式不能为空', trigger: 'blur' }
          ],
          reporterTime: [
            { required: true, message: '报修时间不能为空', trigger: 'blur' }
          ],
          reporterAddr: [
            { required: true, message: '报修地点不能为空', trigger: 'blur' }
          ],
          accidentAddr: [
            { required: true, message: '事故发生地点不能为空', trigger: 'blur' }
          ],
          accidentLabel: [
            { required: true, message: '事故问题标签不能为空', trigger: 'blur' }
          ],
          accidentContent: [
            { required: true, message: '事故描述内容不能为空', trigger: 'blur' }
          ],
          accidentPic: [
            { required: true, message: '事故参考图片不能为空', trigger: 'blur' }
          ],
          resourceRecommend: [
            { required: true, message: '智能推荐抢修资源单位不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '报修单备注不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/rep/reportorder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.reporterOrder = data.reportorder.reporterOrder
                this.dataForm.reporterName = data.reportorder.reporterName
                this.dataForm.reporterPhone = data.reportorder.reporterPhone
                this.dataForm.reporterTime = data.reportorder.reporterTime
                this.dataForm.reporterAddr = data.reportorder.reporterAddr
                this.dataForm.accidentAddr = data.reportorder.accidentAddr
                this.dataForm.accidentLabel = data.reportorder.accidentLabel
                this.dataForm.accidentContent = data.reportorder.accidentContent
                this.dataForm.accidentPic = data.reportorder.accidentPic
                this.dataForm.resourceRecommend = data.reportorder.resourceRecommend
                this.dataForm.remark = data.reportorder.remark
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
              url: this.$http.adornUrl(`/rep/reportorder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'reporterOrder': this.dataForm.reporterOrder,
                'reporterName': this.dataForm.reporterName,
                'reporterPhone': this.dataForm.reporterPhone,
                'reporterTime': this.dataForm.reporterTime,
                'reporterAddr': this.dataForm.reporterAddr,
                'accidentAddr': this.dataForm.accidentAddr,
                'accidentLabel': this.dataForm.accidentLabel,
                'accidentContent': this.dataForm.accidentContent,
                'accidentPic': this.dataForm.accidentPic,
                'resourceRecommend': this.dataForm.resourceRecommend,
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
