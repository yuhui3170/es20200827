<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="仓库名称" clearable></el-input>
      </el-form-item>
		<el-form-item label="供电所" prop="resourceId">
		  <template>
				  <el-select @change="chgResource" v-model="resourceId" placeholder="请选择">
					<el-option 
					  :key="null"
					  label="全 部"
					  :value="null"></el-option>
					<el-option
					  v-for="item in resourcelist"
					  :key="item.resourceId"
					  :label="item.resourceName"
					  :value="item.resourceId">
					</el-option>
				  </el-select>
		  </template>
		</el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('dep:store:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('dep:store:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="storeId"
        header-align="center"
        align="center"
        label="仓库id">
      </el-table-column>
	  <el-table-column
	    prop="resourceName"
	    header-align="center"
	    align="center"
	    label="所属供电所">
	  </el-table-column>
      <el-table-column
        prop="storeName"
        header-align="center"
        align="center"
        label="仓库名称">
      </el-table-column>
      <el-table-column
        prop="storeManagerName"
        header-align="center"
        align="center"
        label="仓库负责人">
      </el-table-column>
      <el-table-column
        prop="managerPhone"
        header-align="center"
        align="center"
        label="仓库联系电话">
      </el-table-column>
	  <el-table-column
	      header-align="center"
	      align="center"
	      label="设备查看">
	  <template slot-scope="scope">
	  	<el-button type="text" size="big" @click="jumpPage(scope.row.storeId)">查 看</el-button>
	  </template>
	    </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.storeId)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.storeId)">删除</el-button>
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
  import AddOrUpdate from './store-add-or-update'
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
	  resourceId:null,
	  resourcelist:[]
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
	  this.resourceId = this.$route.query.resourceId==undefined?null:this.$route.query.resourceId;
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
		let that = this;
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/dep/store/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
			'resourceId': this.resourceId,
            'storeName': this.dataForm.key
          })
        }).then(({data}) => {
			let data_1 = data;
          if (data_1 && data_1.code === 0) {
			  let storelist = data_1.page.list
			  //获取供电所列表
			  	that.$http({
			  	  url: that.$http.adornUrl('/dep/resource/list'),
			  	  method: 'get'
			  	}).then(({data}) => {
					let data_2 = data;
			  	  if (data_2 && data_2.code === 0) {
			  		let resourcelist = data_2.page.list
					this.resourcelist = resourcelist;
						//获取仓管员列表
						this.$http({
						  url: this.$http.adornUrl('/sys/user/getAllStorekeeper'),
						  method: 'post',
						  data: this.$http.adornData()
						}).then(({data}) => {
							let data_3 = data;
							let storekeeperlist = data_3.data;
							console.log(storekeeperlist)
							//循环遍历，使得仓库对应供电所名称和仓管员名称
							if(storelist!=null && resourcelist!=null && storekeeperlist!=null){
								for(var i=0;i<storelist.length;i++){
									for(var j=0;j<resourcelist.length;j++){
										if(storelist[i].resourceId==resourcelist[j].resourceId){
											storelist[i].resourceName = resourcelist[j].resourceName;
										}
									}
									for(var k=0;k<storekeeperlist.length;k++){
										if(storelist[i].storeManager==storekeeperlist[k].user_id){
											storelist[i].storeManagerName = storekeeperlist[k].username;
										}
									}
								}
							}
							this.dataList = storelist;
							this.totalPage = data_1.page.totalCount
						})
			  	  }
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
          return item.storeId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/dep/store/delete'),
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
	jumpPage(storeId){
		this.$router.push({
		  path:'/dep-equipments',
		  query:{
		    storeId: storeId
		  }
		})
	},
	chgResource(){
		this.getDataList();
	}
	
    }
  }
</script>
