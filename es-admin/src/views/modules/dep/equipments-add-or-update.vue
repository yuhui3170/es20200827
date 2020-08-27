<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
	<el-form-item label="所属仓库" prop="storeId">
	  <template>
		<el-select v-model="dataForm.storeId" placeholder="请选择">
		  <el-option
			v-for="item in storelist"
			:key="item.storeId"
			:label="item.storeName"
			:value="item.storeId">
		  </el-option>
		</el-select>
	  </template>
	</el-form-item>
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
    <el-form-item label="设备名称" prop="equipName">
      <el-input v-model="dataForm.equipName" placeholder="设备名称"></el-input>
    </el-form-item>
    <el-form-item label="总数量" prop="equipTotalmount">
      <el-input v-model="dataForm.equipTotalmount" placeholder="设备总数量"></el-input>
    </el-form-item>
    <el-form-item label="当前数量" prop="equipCurrentmount">
      <el-input v-model="dataForm.equipCurrentmount" placeholder="设备当前数量"></el-input>
    </el-form-item>
    <el-form-item label="设备单位" prop="equipUnit">
      <el-input v-model="dataForm.equipUnit" placeholder="设备单位"></el-input>
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
          equipId: 0,
          storeId: '',
		  typeId:'',
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
          typeId: [
            { required: true, message: '设备所属类别不能为空', trigger: 'blur' }
          ]
        },
		storelist:[],
		typelist:[]
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
				this.dataForm.typeId = data.equipments.typeId
                this.dataForm.equipName = data.equipments.equipName
                this.dataForm.equipTotalmount = data.equipments.equipTotalmount
                this.dataForm.equipCurrentmount = data.equipments.equipCurrentmount
                this.dataForm.equipUnit = data.equipments.equipUnit
                this.dataForm.remark = data.equipments.remark
              }
            })
          }
        })
		//获取仓库列表
		this.$http({
		  url: this.$http.adornUrl('/dep/store/list'),
		  method: 'get'
		}).then(({data}) => {
		  if (data && data.code === 0) {
		    this.storelist = data.page.list
		  } else {
		    this.storelist = []
		  }
		})
		//获取设备分类列表
		this.$http({
		  url: this.$http.adornUrl('/dep/equipmentstype/list'),
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
              url: this.$http.adornUrl(`/dep/equipments/${!this.dataForm.equipId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'equipId': this.dataForm.equipId || undefined,
                'storeId': this.dataForm.storeId,
				'typeId': this.dataForm.typeId,
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
