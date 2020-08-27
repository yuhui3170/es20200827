<template>
  <div class="mod-demo-echarts">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_chartLineBox" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card>
          <div id="J_chartBarBox" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card>
          <div id="J_chartPieBox" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card>
          <div id="J_chartScatterBox" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import echarts from "echarts";
export default {
  data() {
    return {
      chartLine: null,
      urgentData: [],
      chartLineLehands: [],
      chartLineDate: [],
      chartLineSeries: [],

      chartBar: null,
      LabelTypeData: [],
      chartBarLehands: [],
      chartBarDate: [],
      chartBarSeries: [],

      chartPie: null,
      chartPieLehands: [],
      chartPieSeries: [],

      chartScatter: null,
      EquipAnaData: [],
      EquipAnaLehands: [],
      StoreApplyData: [],
      StoreApplyTypesData: [],
      StoreApplyEquipData: []
    };
  },
  mounted() {
    //获取数据
    this.getUrgentData();
    this.getLabelTypeData();

    this.getEquipAnaData();

    //
  },
  activated() {
    // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
    if (this.chartLine) {
      this.chartLine.resize();
    }
    if (this.chartBar) {
      this.chartBar.resize();
    }
    if (this.chartPie) {
      this.chartPie.resize();
    }
    if (this.chartScatter) {
      this.chartScatter.resize();
    }
  },
  methods: {
    //getUrgentData
    getUrgentData() {
      this.$http({
        url: this.$http.adornUrl("/rep/urgentorder/analysis/UrgentData"),
        method: "get"
      }).then(({ data }) => {
        this.urgentData = data.data;

        this.urgentData.forEach(resource => {
          let chartLineSeriesObj = {
            name: "",
            type: "line",
            stack: "总量",
            data: []
          };

          this.chartLineLehands.push(resource.resourceName);
          chartLineSeriesObj.name = resource.resourceName;

          resource.orderinfo.forEach(orderinfo => {
            if (this.chartLineDate.length < 12) {
              this.chartLineDate.push(orderinfo.mon);
            }
            chartLineSeriesObj.data.push(orderinfo.orders);
          });

          this.chartLineSeries.push(chartLineSeriesObj);
        });

        this.initChartLine();
      });
    },

    getLabelTypeData() {
      this.$http({
        url: this.$http.adornUrl("/rep/labelstype/analysis/LabelOrders"),
        method: "get"
      }).then(({ data }) => {
        this.LabelTypeData = data.data;

        this.LabelTypeData.forEach(labelType => {
          let chartBarObj = {
            name: "",
            type: "bar",
            stack: "分类",
            data: []
          };

          let chatPieObj = {
            value: "",
            name: ""
          };

          let orders = 0;

          this.chartBarLehands.push(labelType.typeName);
          this.chartPieLehands.push(labelType.typeName);

          chartBarObj.name = labelType.typeName;
          chatPieObj.name = labelType.typeName;

          labelType.orderinfo.forEach(orderinfo => {
            if (this.chartBarDate.length < 12) {
              this.chartBarDate.push(orderinfo.mon);
            }

            chartBarObj.data.push(orderinfo.orders);

            orders += orderinfo.orders;
          });

          chatPieObj.value = "" + orders;

          this.chartBarSeries.push(chartBarObj);

          this.chartPieSeries.push(chatPieObj);
        });

        this.initChartBar();
        this.initChartPie();
      });
    },

    getEquipAnaData() {
      this.$http({
        url: this.$http.adornUrl("/dep/applyinfo/analyse/applyAnaInfo"),
        method: "get"
      }).then(({ data }) => {
        this.EquipAnaData = data.data;

        let stores = [];
        let storeTypes = [];
        let storeEquips = [];
        //获取仓库列表
        this.EquipAnaData.forEach(info => {
          let StoreApplyDataObj = {
            value: 0,
            name: ""
          };

          let StoreApplyTypesObj = {
            value: 0,
            name: ""
          };

          let StoreApplyEquipObj = {
            value: 0,
            name: ""
          };

          //   if (this.EquipAnaLehands.indexOf(info.equipName) == -1) {
          //     this.EquipAnaLehands.push(info.equipName);
          //   }

          if (stores.indexOf(info.storeName) == -1) {
            stores.push(info.storeName);
            StoreApplyDataObj.name = info.storeName;
            this.StoreApplyData.push(StoreApplyDataObj);
          }

          if (storeTypes.indexOf(info.storeName + "-" + info.typeName) == -1) {
            storeTypes.push(info.storeName + "-" + info.typeName);
            StoreApplyTypesObj.name = info.storeName + "-" + info.typeName;
            this.StoreApplyTypesData.push(StoreApplyTypesObj);
          }

          if (
            storeEquips.indexOf(
              info.storeName + "-" + info.typeName + "-" + info.equipName
            ) == -1
          ) {
            storeEquips.push(
              info.storeName + "-" + info.typeName + "-" + info.equipName
            );

            this.EquipAnaLehands.push(
              info.storeName + "-" + info.typeName + "-" + info.equipName
            );

            StoreApplyEquipObj.name =
              info.storeName + "-" + info.typeName + "-" + info.equipName;
            this.StoreApplyEquipData.push(StoreApplyEquipObj);
          }
        });

        console.log("EquipAnaLehands " + JSON.stringify(this.EquipAnaLehands));

        //计算各个仓库申请设备总次数
        this.StoreApplyData.forEach(StoreApply => {
          let counts = 0;
          this.EquipAnaData.forEach(info => {
            if (info.storeName == StoreApply.name) {
              counts += info.counts;
            }
          });
          StoreApply.value = counts;
        });

        console.log("StoreApplyData " + JSON.stringify(this.StoreApplyData));

        //计算仓库+类别 申请总次数
        this.StoreApplyTypesData.forEach(StoreApplyTypes => {
          let counts = 0;
          this.EquipAnaData.forEach(info => {
            if (info.storeName + "-" + info.typeName == StoreApplyTypes.name) {
              counts += info.counts;
            }
          });

          StoreApplyTypes.value = counts;
        });

        console.log(
          "StoreApplyTypesData " + JSON.stringify(this.StoreApplyTypesData)
        );

        //计算仓库+类别+设备 申请总次数
        this.StoreApplyEquipData.forEach(StoreApplyEquip => {
          let counts = 0;
          this.EquipAnaData.forEach(info => {
            if (
              info.storeName + "-" + info.typeName + "-" + info.equipName ==
              StoreApplyEquip.name
            ) {
              counts += info.counts;
            }
          });

          StoreApplyEquip.value = counts;
        });

        console.log(
          "StoreApplyEquipData " + JSON.stringify(this.StoreApplyEquipData)
        );

        this.initEquipmentsChartPie();
      });
    },

    // 折线图
    initChartLine() {
      var option = {
        title: {
          text: "维修单统计",
          left: "right"
        },
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: this.chartLineLehands
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        toolbox: {
        //   feature: {
        //     saveAsImage: {}
        //   }
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: this.chartLineDate
        },
        yAxis: {
          type: "value"
        },
        series: this.chartLineSeries
      };
      this.chartLine = echarts.init(document.getElementById("J_chartLineBox"));
      this.chartLine.setOption(option);
      window.addEventListener("resize", () => {
        this.chartLine.resize();
      });
    },
    // 柱状图
    initChartBar() {
      var option = {
        title: {
          text: "故障分类统计",
          left: "right"
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow"
          }
        },
        legend: {
          data: this.chartBarLehands
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: [
          {
            type: "category",
            data: this.chartBarDate
          }
        ],
        yAxis: [
          {
            type: "value"
          }
        ],
        series: this.chartBarSeries
      };
      this.chartBar = echarts.init(document.getElementById("J_chartBarBox"));
      this.chartBar.setOption(option);
      window.addEventListener("resize", () => {
        this.chartBar.resize();
      });
    },
    // 饼状图
    initChartPie() {
      var option = {
        //backgroundColor: "#2c343c",
        title: {
          text: "故障分类占比统计",
          left: "right",
          top: 20
          //   textStyle: {
          //     color: "#ccc"
          //   }
        },

        legend: {
          orient: "vertical", // 标签名称垂直排列
          x: "left", // 标签的位置
          data: this.chartBarLehands
        },

        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        visualMap: {
          show: false,
          min: 80,
          max: 600,
          inRange: {
            colorLightness: [0, 1]
          }
        },
        series: [
          {
            name: "故障类别",
            type: "pie",
            radius: "95%",
            center: ["50%", "50%"],
            label: {
              normal: {
                show: true,
                formatter: "{b}({d}%)" // 显示百分比
              }
            },
            data: this.chartPieSeries.sort(function(a, b) {
              return a.value - b.value;
            }),
            roseType: "area",
            labelLine: {
              normal: {
                smooth: 0.2,
                length: 10,
                length2: 20
              }
            },
            itemStyle: {
              normal: {
                shadowBlur: 200,
                shadowColor: "rgba(50, 2, 10, 0.5)"
              }
            },
            animationType: "scale",
            animationEasing: "elasticOut",
            animationDelay: function(idx) {
              return Math.random() * 200;
            }
          }
        ]
      };
      this.chartPie = echarts.init(document.getElementById("J_chartPieBox"));
      this.chartPie.setOption(option);
      window.addEventListener("resize", () => {
        this.chartPie.resize();
      });
    },
    // 设备数据pie
    initEquipmentsChartPie() {
      var option = {
        title: {
          text: "设备申请占比统计",
          left: "right",
          top: 20
          //   textStyle: {
          //     color: "#ccc"
          //   }
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
          orient: "vertical",
          left: 0,
          data: this.EquipAnaLehands
        },
        series: [
          {
            name: "访问来源",
            type: "pie",
            selectedMode: "single",
            radius: [0, "25%"],
            center: ["80%", "50%"],
            label: {
              position: "inner"
            },
            labelLine: {
              show: false
            },
            data: this.StoreApplyData
          },
          {
            name: "访问来源",
            type: "pie",
            selectedMode: "single",
            radius: ["35%", "50%"],
            center: ["80%", "50%"],
            label: {
              normal: {
                show: true,
                formatter: "{b}({d}%)" // 显示百分比
              },
              position: "inner"
            },
            labelLine: {
              show: false
            },
            data: this.StoreApplyTypesData
          },
          {
            name: "访问来源",
            type: "pie",
            radius: ["70%", "85%"],
            center: ["80%", "50%"],
            label: {
              normal: {
                show: false,
                formatter: "{b}({d}%)" // 显示百分比
              },
              formatter: "{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ",
              backgroundColor: "#eee",
              borderColor: "#aaa",
              borderWidth: 1,
              borderRadius: 4,
              rich: {
                a: {
                  color: "#999",
                  lineHeight: 22,
                  align: "center"
                },
                hr: {
                  borderColor: "#aaa",
                  width: "100%",
                  borderWidth: 0.5,
                  height: 0
                },
                b: {
                  fontSize: 16,
                  lineHeight: 33
                },
                per: {
                  color: "#eee",
                  backgroundColor: "#334455",
                  padding: [2, 4],
                  borderRadius: 2
                }
              }
            },
            data: this.StoreApplyEquipData,
            itemStyle: {
              normal: {
                shadowBlur: 200,
                shadowColor: "rgba(120, 2, 10, 0.5)"

                // color: function(params) {
                //   //自定义颜色
                //   var colorList = [
                //     "#00FFFF",
                //     "#00FF00",
                //     "#FFFF00",
                //     "#FF8C00",
                //     "#FF0000",
                //     "#FE8463"
                //   ];
                //   return colorList[params.dataIndex];
                // }
              }
            }
          }
        ]
      };
      this.chartPie = echarts.init(
        document.getElementById("J_chartScatterBox")
      );
      this.chartPie.setOption(option);
      window.addEventListener("resize", () => {
        this.chartPie.resize();
      });
    }
  }
};
</script>

<style lang="scss">
.mod-demo-echarts {
  > .el-alert {
    margin-bottom: 10px;
  }
  > .el-row {
    margin-top: -10px;
    margin-bottom: -10px;
    .el-col {
      padding-top: 10px;
      padding-bottom: 10px;
    }
  }
  .chart-box {
    min-height: 400px;
  }
}
</style>
