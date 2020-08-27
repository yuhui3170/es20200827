<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="仓库id" prop="storeId">
      <el-input v-model="dataForm.storeId" placeholder="仓库id"></el-input>
    </el-form-item>
    <el-form-item label="设备名称" prop="equipName">
      <el-input v-model="dataForm.equipName" placeholder="设备名称"></el-input>
    </el-form-item>
    <el-form-item label="设备总数量" prop="equipTotalmount">
      <el-input v-model="dataForm.equipTotalmount" placeholder="设备总数量"></el-input>
    </el-form-item>
    <el-form-item label="设备当前数量" prop="equipCurrentmount">
      <el-input v-model="dataForm.equipCurrentmount" placeholder="设备当前数量"></el-input>
    </el-form-item>
    <el-form-item label="设备单位" prop="equipUnit">
      <el-input v-model="dataForm.equipUnit" placeholder="设备单位"></el-input>
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
          equipId: 0,
          storeId: '',
          equipName: '',
          equipTotalmount: '',
          equipCurrentmount: '',
          equipUnit: '',
          remark: ''
        },
        dataRule: {
          storeId: [
            { required: true, message: '仓库id不能为空', trigger: 'blur' }
          ],
          equipName: [
            { required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          equipTotalmount: [
            { required: true, message: '设备总数量不能为空', trigger: 'blur' }
          ],
          equipCurrentmount: [
            { required: true, message: '设备当前数量不能为空', trigger: 'blur' }
          ],
          equipUnit: [
            { required: true, message: '设备单位不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.equipId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.equipId) {
            this.$http({
              url: this.$http.adornUrl(`/dep/equipments/info/${this.dataForm.equipId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.storeId = data.equipments.storeId
                this.dataForm.equipName = data.equipments.equipName
                this.dataForm.equipTotalmount = data.equipments.equipTotalmount
                this.dataForm.equipCurrentmount = data.equipments.equipCurrentmount
                this.dataForm.equipUnit = data.equipments.equipUnit
                this.dataForm.remark = data.equipments.remark
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
              url: this.$http.adornUrl(`/dep/equipments/${!this.dataForm.equipId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'equipId': this.dataForm.equipId || undefined,
                'storeId': this.dataForm.storeId,
                'equipName': this.dataForm.equipName,
                'equipTotalmount': this.dataForm.equipTotalmount,
                'equipCurrentmount': this.dataForm.equipCurrentmount,
                'equipUnit': this.dataForm.equipUnit,
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
