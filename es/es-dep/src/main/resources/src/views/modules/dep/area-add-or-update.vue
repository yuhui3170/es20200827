<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="区域名称" prop="areaName">
      <el-input v-model="dataForm.areaName" placeholder="区域名称"></el-input>
    </el-form-item>
    <el-form-item label="上级区域id" prop="parentId">
      <el-input v-model="dataForm.parentId" placeholder="上级区域id"></el-input>
    </el-form-item>
    <el-form-item label="区域等级 0-省 1-市 2-县/区" prop="areaLevel">
      <el-input v-model="dataForm.areaLevel" placeholder="区域等级 0-省 1-市 2-县/区"></el-input>
    </el-form-item>
    <el-form-item label="显示0 不显示1" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="显示0 不显示1"></el-input>
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
          areaId: 0,
          areaName: '',
          parentId: '',
          areaLevel: '',
          showStatus: ''
        },
        dataRule: {
          areaName: [
            { required: true, message: '区域名称不能为空', trigger: 'blur' }
          ],
          parentId: [
            { required: true, message: '上级区域id不能为空', trigger: 'blur' }
          ],
          areaLevel: [
            { required: true, message: '区域等级 0-省 1-市 2-县/区不能为空', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: '显示0 不显示1不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.areaId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.areaId) {
            this.$http({
              url: this.$http.adornUrl(`/dep/area/info/${this.dataForm.areaId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.areaName = data.area.areaName
                this.dataForm.parentId = data.area.parentId
                this.dataForm.areaLevel = data.area.areaLevel
                this.dataForm.showStatus = data.area.showStatus
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
              url: this.$http.adornUrl(`/dep/area/${!this.dataForm.areaId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'areaId': this.dataForm.areaId || undefined,
                'areaName': this.dataForm.areaName,
                'parentId': this.dataForm.parentId,
                'areaLevel': this.dataForm.areaLevel,
                'showStatus': this.dataForm.showStatus
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
