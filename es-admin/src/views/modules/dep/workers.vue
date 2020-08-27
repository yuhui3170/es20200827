<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="真实姓名/联系方式" clearable></el-input>
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
        <el-button v-if="isAuth('dep:workers:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('dep:workers:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="workerId"
        header-align="center"
        align="center"
        label="工人id">
      </el-table-column>
      <el-table-column
        prop="resourceName"
        header-align="center"
        align="center"
        label="所属供电所">
      </el-table-column>
      <el-table-column
        prop="workerName"
        header-align="center"
        align="center"
        label="真实姓名">
      </el-table-column>
	  <el-table-column
	    prop="workNickname"
	    header-align="center"
	    align="center"
	    label="用户名">
	  </el-table-column>
	  <el-table-column
	      prop="workPic"
	      header-align="center"
	      align="center"
	      label="头像">
	  	<template slot-scope="scope">
	  　　　　<img v-if="scope.row.workPic!=null" :src="scope.row.picUrl" class="head_pic"/>
	  　　</template>
	  </el-table-column>
	  <el-table-column
	    prop="workMobile"
	    header-align="center"
	    align="center"
	    label="联系方式">
	  </el-table-column>
      <el-table-column
        prop="workerBirth"
        header-align="center"
        align="center"
        label="年龄">
		<template slot-scope="scope">{{ scope.row.workerBirth | ageFilter}}</template>
      </el-table-column>
      <el-table-column
        prop="ordersReceived"
        header-align="center"
        align="center"
        label="接单数量">
      </el-table-column>
      <el-table-column
        prop="ordersFinished"
        header-align="center"
        align="center"
        label="结单数量">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.workerId)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.workerId)">删除</el-button>
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
  import AddOrUpdate from './workers-add-or-update'
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
          url: this.$http.adornUrl('/dep/workers/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'resourceId': this.resourceId,
			'workerName': this.dataForm.key,
			'workMobile': this.dataForm.key
          })
        }).then(({data}) => {
		  let data_1 = data;
          if (data_1 && data_1.code === 0) {
            let workerlist = data_1.page.list
			//获取供电所列表
				that.$http({
				  url: that.$http.adornUrl('/dep/resource/list'),
				  method: 'get'
				}).then(({data}) => {
					let data_2 = data;
				  if (data_2 && data_2.code === 0) {
					let resourcelist = data_2.page.list
					this.resourcelist = resourcelist;
					//循环遍历，使得抢修人员获取对应供电所名称
					if(workerlist!=null && resourcelist!=null){
						for(var i=0;i<workerlist.length;i++){
							for(var j=0;j<resourcelist.length;j++){
								if(workerlist[i].resourceId==resourcelist[j].resourceId){
									workerlist[i].resourceName = resourcelist[j].resourceName;
								}
							}
						}
					}
					this.dataList = workerlist;
					//头像url处理
					for(let i=0;i<this.dataList.length;i++){
						if(this.dataList[i].workPic!=null){
							this.dataList[i].picUrl = that.$http.adornUrl('/dep/file/picture/download?path='+encodeURIComponent(this.dataList[i].workPic));
						}
					}
					this.totalPage = data_1.page.totalCount
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
          return item.workerId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/dep/workers/delete'),
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
	  chgResource(){
		  this.getDataList();
	  }
	  
    }
  }
</script>
<style>
	.head_pic{
		width:80px;
		height:80px;
		border-radius:5%;
	}
</style>