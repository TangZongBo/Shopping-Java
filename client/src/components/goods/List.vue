<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>商品列表</el-breadcrumb-item>
        </el-breadcrumb>
        <!--卡片视图区域-->
        <el-card>
             <el-row :gutter="20">
                 <el-col :span="8">
                     <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @close="getGoodsList">
                         <el-button slot="append" icon="el-icon-search" @click="getGoodsList"></el-button>
                     </el-input>
                 </el-col>
                 <el-col :span="4">
                     <el-button type="primary" @click="goAddGood">添加商品</el-button>
                 </el-col>
             </el-row>

             <!--商品表格-->
            <el-table :data="goodsList" border stripe>
                  <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="商品名称" prop="goods_name" width="140px"></el-table-column>
                <el-table-column label="商品价格(元)" prop="goods_price"></el-table-column>
                <el-table-column label="商品重量" prop="goods_weight"></el-table-column>
                <el-table-column label="商品创建时间" prop="add_time">
                       <template slot-scope="scope">
                           {{scope.row.add_time | dateFormat}}
                       </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" class="el-icon-edit" size="mini"></el-button>
                        <el-button type="danger" class="el-icon-delete" size="mini" @click="removeById(scope.row.goods_id)"></el-button>
                    </template>
                </el-table-column>
            </el-table>

             <!--分页区域-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pagenum"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="queryInfo.pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total" background>
            </el-pagination>

        </el-card>
    </div>
</template>

<script>
    export default {
        data(){
            return{
                queryInfo:{
                    query:'',
                    pagenum:1,
                    pagesize:5
                },
                //商品列表
                goodsList:[],
                //总条数
                total:0
            }
        },
        created() {
              this.getGoodsList()
        },
        methods:{
            //获取商品列表
             async getGoodsList(){
                   /*const {data:res} = await this.$http.get('goods')
                   if(res.meta.status!==200){
                       return this.$message.error('获取商品列表失败!')
                   }
                   this.goodsList = res.data
                   this.total = res.data.total*/
                   this.goodsList = [
                       {
                       "goods_id":144,
                       "goods_name":"一加7pro",
                       "goods_price":1,
                       "goods_number":1,
                       "goods_weight":1,
                       "goods_state":null,
                       "add_time":1512954923,
                       "upd_time":1512954923,
                       "hot_mmumber":0,
                       "is_promote":false
                       },
                       {
                           "goods_id":145,
                           "goods_name":"oppo ace",
                           "goods_price":1,
                           "goods_number":1,
                           "goods_weight":1,
                           "goods_state":null,
                           "add_time":1512954923,
                           "upd_time":1512954923,
                           "hot_mmumber":0,
                           "is_promote":false
                       },
                       {
                           "goods_id":146,
                           "goods_name":"黑鲨6",
                           "goods_price":1,
                           "goods_number":1,
                           "goods_weight":1,
                           "goods_state":null,
                           "add_time":1512954923,
                           "upd_time":1512954923,
                           "hot_mmumber":0,
                           "is_promote":false
                       }

                       ]
                   this.total = this.goodsList.length
                  // return this.$message.success('获取商品列表成功!')
             },
            //pagesize改变就会调用
            handleSizeChange(newSize){
                  this.queryInfo.pagesize = newSize
                  this.getGoodsList()
            },
            //pagenum改变就会调用
            handleCurrentChange(newPage){
                  this.queryInfo.pagenum = newPage
                this.getGoodsList()
            },
            async removeById(id){
                const confirmResult = await this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).catch(err => err)

                if(confirmResult!=='confirm'){
                    this.$message.info('用户已经取消删除!')
                }
                /*const {data:res} = await this.$http.delete(`goods/${id}`)
                if(res.meta.status!==200){
                    return this.$message.error('删除失败!')
                }*/

                this.$message.success('删除成功!')
                //重新获取商品列表
                this.getGoodsList()
            },
            //添加商品
            goAddGood(){
                //跳转
                 this.$router.push('/goods/add')
            }

        }
    }
</script>

<style lang="less" scoped>

</style>
