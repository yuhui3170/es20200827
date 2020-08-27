<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="设备名称" clearable></el-input>
      </el-form-item>
		<el-form-item label="所属仓库" prop="storeId">
		  <template>
			<el-select @change="chgStore" v-model="storeId" placeholder="请选择">
			  <el-option
				:key="null"
				label="全 部"
				:value="null">
			  </el-option>
			  <el-option
				v-for="item in storelist"
				:key="item.storeId"
				:label="item.storeName"
				:value="item.storeId">
			  </el-option>
			</el-select>
		  </template>
		</el-form-item>
		<el-form-item label="分类" prop="typeId">
		  <template>
			<el-select @change="chgType" v-model="typeId" placeholder="请选择">
			  <el-option
				:key="null"
				label="全 部"
				:value="null">
			  </el-option>
			  <el-option
				v-for="item in typelist"
				:key="item.typeId"
				:label="item.typeName"
				:value="item.typeId">
			  </el-option>
			</el-select>
		  </template>
		</el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('dep:equipments:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('dep:equipments:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="equipId"
        header-align="center"
        align="center"
        label="设备id">
      </el-table-column>
      <el-table-column
        prop="storeName"
        header-align="center"
        align="center"
        label="所属仓库">
      </el-table-column>
	  <el-table-column
	    prop="typeName"
	    header-align="center"
	    align="center"
	    label="所属分类">
		<template slot-scope="scope">{{scope.row.typeId|keyvalFilter('typeId',typelist,'typeName')}}</template>
	  </el-table-column>
      <el-table-column
        prop="equipName"
        header-align="center"
        align="center"
        label="设备名称">
      </el-table-column>
      <el-table-column
        prop="equipTotalmount"
        header-align="center"
        align="center"
        label="设备总数量">
      </el-table-column>
      <el-table-column
        prop="equipCurrentmount"
        header-align="center"
        align="center"
        label="设备当前数量">
      </el-table-column>
      <el-table-column
        prop="equipUnit"
        header-align="center"
        align="center"
        label="设备单位">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.equipId)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.equipId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './equipments-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
		storeId:null,
		storelist:[],
		typeId:null,
		typelist:[]
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
	  this.storeId = this.$route.query.storeId==undefined?null:this.$route.query.storeId;
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/dep/equipments/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
			'storeId': this.storeId,
			'typeId': this.typeId,
            'equipName': this.dataForm.key
          })
        }).then(({data}) => {
			let data_1 = data;
          if (data_1 && data_1.code === 0) {
            let equipmentslist = data_1.page.list
            	//获取仓库列表
            	this.$http({
            	  url: this.$http.adornUrl('/dep/store/list'),
            	  method: 'post',
            	  data: this.$http.adornData()
            	}).then(({data}) => {
            		let data_2 = data;
            		let storelist = data_2.page.list;
					this.storelist = storelist;
					//获取设备分类列表
					this.$http({
					  url: this.$http.adornUrl('/dep/equipmentstype/list'),
					  method: 'get',
					  data: this.$http.adornData()
					}).then(({data}) => {
					  let data_3 = data;
					  if (data_3 && data_3.code === 0) {
					    let typelist = data_3.page.list
						this.typelist = typelist;
					    //循环遍历，使得备品备件对应仓库
					    if(equipmentslist!=null && storelist!=null && typelist!=null){
					    	for(var i=0;i<equipmentslist.length;i++){
					    		for(var j=0;j<storelist.length;j++){
					    			if(equipmentslist[i].storeId==storelist[j].storeId){
					    				equipmentslist[i].storeName = storelist[j].storeName;
					    			}
					    		}
					    	}
					    }
					    this.dataList = equipmentslist;
					    this.totalPage = data_1.page.totalCount
					  }
					})
            	})
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.equipId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/dep/equipments/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
	  chgStore(){
		  this.getDataList();
	  },
	  chgType(){
		  this.getDataList();
	  }
    }
  }
</script>
