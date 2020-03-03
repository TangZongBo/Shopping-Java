<template>
      <div>
          <!--面包屑导航区域-->
          <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>商品管理</el-breadcrumb-item>
              <el-breadcrumb-item>商品分类</el-breadcrumb-item>
          </el-breadcrumb>

          <!--卡片视图区域-->
          <el-card>
              <el-row>
                  <el-col>
                      <el-button type="primary" @click="showAddCateDialog">添加分类</el-button>
                  </el-col>
              </el-row>

              <!--表格-->
              <tree-table class="treeTable" :data="cateList" :columns="columns" :expand-type="false"
                          :selection-type="false" show-index index-text="#" border :show-row-hover="false">
                  <!--是否有效-->
                  <template slot="isok" slot-scope="scope">
                            <i class="el-icon-success" v-if="scope.row.cat_deleted === false" style="color:lightgreen;"></i>
                           <i class="el-icon-error" v-else style="color: red;"></i>
                       </template>
                  <!--排序-->
                  <template slot="order" slot-scope="scope">
                      <el-tag v-if="scope.row.cat_level===0" size="mini">一级</el-tag>
                      <el-tag v-else-if="scope.row.cat_level===1" type="success" size="mini">二级</el-tag>
                      <el-tag v-else type="warning" size="mini">三级</el-tag>
                  </template>

                  <!--操作-->
                  <template slot="ops" slot-scope="scope">
                      <el-button type="primary" class="el-icon-edit" size="mini">编辑</el-button>
                      <el-button type="danger" class="el-icon-delete" size="mini">删除</el-button>
                  </template>
              </tree-table>
              <!--分页区域-->
              <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="queryInfo.pagenum"
                      :page-sizes="[3,5,8,15]"
                      :page-size="queryInfo.pagesize"
                      layout="total, sizes, prev, pager, next, jumper"
                      :total="total">
              </el-pagination>

          </el-card>

          <!--添加分类对话框-->
          <el-dialog
                  title="添加分类"
                  :visible.sync="addCateDialogVisible"
                  width="50%" @close="addCateDialogClosed">
              <!--添加分类表单-->
              <el-form ref="addCateFormRef" :model="addCateForm" :rules="addCateFormRules"  label-width="100px">
                  <el-form-item label="分类名称:" prop="cat_name">
                      <el-input v-model="addCateForm.cat_name"></el-input>
                  </el-form-item>

                  <el-form-item label="父级分类:">
                      <!--options:数据源 props:指定配置对象-->
                      <el-cascader :options="parentCateList" v-model="selectedKeys"
                                   :props="cascaderProps"
                              @change="parentCateChanged" clearable change-on-select></el-cascader>
                  </el-form-item>

              </el-form>


              <span slot="footer" class="dialog-footer">
                <el-button @click="addCateDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCate">确 定</el-button>
              </span>
          </el-dialog>
      </div>
</template>

<script>
    export default {
     data(){
         return{
             //查询条件
             queryInfo:{
                 type:3,
                 pagenum:1,
                 pagesize:5
             },
             //商品分类的数据 默认为空
             cateList:[],
             //总数据条数
             total:0,
             columns:[
                 {
                     label:'分类名称',
                     prop:'cat_name'
                 },
                 {
                     label:'是否有效',
                     //表示,将当前列定义为模板列
                     type:'template',
                     //表示当前这一列使用模板名称
                     template:'isok'
                 },
                 {
                     label:'排序',
                     //表示,将当前列定义为模板列
                     type:'template',
                     //表示当前这一列使用模板名称
                     template:'order'
                 },
                 {
                     label:'操作',
                     //表示,将当前列定义为模板列
                     type:'template',
                     //表示当前这一列使用模板名称
                     template:'ops'
                 }
             ],
             //添加分类对话框隐藏与显示
             addCateDialogVisible:false,
             //添加分类表单
             addCateForm:{
                 //分类名称
                 cat_name:'',
                 //分类父id
                 cat_pid:0,
                 //分类登记,默认添加的是1级分类
                 cat_level:0
             },
             //添加分类表单的验证规则对象
             addCateFormRules:{
                 cat_name:[
                     {required:true,message:'请输入分类名称',trigger:'blur'}
                 ]
             },
             //父级分类列表
             parentCateList:[],
             //添加分类级联选择器配置对象
             cascaderProps:{
                 value:'cat_id',
                 label:'cat_name',
                 children:'children',
                 expandTrigger: 'hover'
             },
             //选中的父级分类id数组
             selectedKeys:[]
         }
     },
        created(){
           this.getCateList()

        },

        methods:{
           //获取商品分类数据
            async getCateList(){
                 /*const {data:res} = await this.$http.get('categories',{params:this.queryInfo})

                if(res.meta.status!==200){
                    this.$message.error('获取商品分类失败!')
                }*/

                this.cateList = [
                    {
                        "cat_id":1,
                        "cat_name":"大家电",
                        "cat_pid":0,
                        "cat_level":0,
                        "cat_deleted":false,
                        "children":[
                            {
                                "cat_id":3,
                                "cat_name":"电视",
                                "cat_pid":1,
                                "cat_level":1,
                                "cat_deleted":false,
                                 "children":[
                                     {
                                         "cat_id":6,
                                         "cat_name":"曲面电视",
                                         "cat_pid":3,
                                         "cat_level":2,
                                         "cat_deleted":false
                                     },
                                     {
                                         "cat_id":7,
                                         "cat_name":"海信",
                                         "cat_pid":3,
                                         "cat_level":2,
                                         "cat_deleted":false
                                     }
                                 ]
                            }
                        ]
                    },
                    {
                        "cat_id":8,
                        "cat_name":"手机",
                        "cat_pid":0,
                        "cat_level":0,
                        "cat_deleted":false,
                        "children":[
                            {
                                "cat_id":10,
                                "cat_name":"曲面手机",
                                "cat_pid":8,
                                "cat_level":1,
                                "cat_deleted":false,
                                "children":[
                                    {
                                        "cat_id":11,
                                        "cat_name":"小米",
                                        "cat_pid":10,
                                        "cat_level":2,
                                        "cat_deleted":true
                                    },
                                    {
                                        "cat_id":12,
                                        "cat_name":"一加",
                                        "cat_pid":10,
                                        "cat_level":2,
                                        "cat_deleted":false
                                    }
                                ]
                            }
                        ]
                    }
                ]
                console.log(this.cateList)
                this.total = this.cateList.length
                console.log(this.total)
            },
            //监听pagesize改变
            handleSizeChange(newSize){
                 this.queryInfo.pagesize = newSize
                this.getCateList()
            },
            //监听pagenum 页码值改变
            handleCurrentChange(newPage){
                this.queryInfo.pagenum = newPage
                this.getCateList()
            },
            showAddCateDialog(){
                //先获取父级分类列表
                this.getParentCateList()

                this.addCateDialogVisible = true
            },
            //获取父级分类节点
            async getParentCateList(){
                /*const {data:res} = await this.$http.get('categories',{params:{type:2}})
                if(res.data.status!==200){
                    this.$message.error('获取父级分类失败!')
                }
                 this.parentCateList = res.data*/
                 this.parentCateList = [
                     {
                         "cat_id":1,
                         "cat_name":"大家电",
                         "cat_pid":0,
                         "cat_level":0,
                         "cat_deleted":false,
                         "children":[
                             {
                                 "cat_id":3,
                                 "cat_name":"电视",
                                 "cat_pid":1,
                                 "cat_level":1,
                                 "cat_deleted":false
                             }
                         ]
                     },
                     {
                         "cat_id":8,
                         "cat_name":"手机",
                         "cat_pid":0,
                         "cat_level":0,
                         "cat_deleted":false,
                         "children":[
                             {
                                 "cat_id":10,
                                 "cat_name":"曲面手机",
                                 "cat_pid":8,
                                 "cat_level":1,
                                 "cat_deleted":false
                             }
                         ]
                     }
                 ]
            },
            //选择项发生变化
            parentCateChanged(){
                console.log(this.selectedKeys)

                if(this.selectedKeys.length>0){

                    this.addCateForm.cat_pid = this.selectedKeys[this.selectedKeys.length-1]
                    this.addCateForm.cat_level = this.selectedKeys.length
                    return
                }else{
                    this.addCateForm.cat_pid=0
                    this.addCateForm.cat_level=0
                }
            },

            //点击确定
            addCate(){
                console.log(this.addCateForm)
                this.$refs.addCateFormRef.validate(async valid =>{
                     if(!valid)return
                    const {data:res} = await this.$http.post('categories',this.addCateForm)
                    if(res.meta.status!==200){
                        this.$message.error('添加分类失败!')
                    }
                     this.$message.success('添加分类成功!')
                    //刷新数据列表
                    this.getCateList()
                    //隐藏对话框
                    this.addCateDialogVisible = false
                })
            },

            //监听对话框关闭事件 重置表单
            addCateDialogClosed(){
                this.$refs.addCateFormRef.resetFields()
                this.selectedKeys = []
                this.addCateForm.cat_level = 0
                this.addCateForm.cat_pid = 0
            }
        }
    }
</script>

<style lang="less" scoped>
    .treeTable{
        margin-top: 15px;
    }

    .el-cascader{
        width: 100%;
    }
</style>
