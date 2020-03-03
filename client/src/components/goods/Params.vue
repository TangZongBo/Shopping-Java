<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>参数列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图区域-->
        <el-card class="box-card">
            <!--警告区域-->
            <el-alert show-icon title="注意：只允许为第三级分类设置相关参数！" type="warning" :closable="false"></el-alert>

            <!--选择商品分类区域-->
            <el-row class="cat_opt">
                 <el-col>
                     <span>选择商品分类:</span>
                     <!--选择商品分类的级联选择框-->
                     <el-cascader style="margin-left: 15px" :options="cateList" v-model="selectedCateKeys"
                                  :props="cateProps"
                                  @change="handleChange" clearable change-on-select></el-cascader>
                 </el-col>
            </el-row>

            <!--tag页签区域-->
            <el-tabs v-model="activeName" @tab-click="handleTagClick">
                <!--添加动态参数列表-->
                <el-tab-pane label="动态参数" name="many">
                    <!--添加参数按钮-->
                    <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addDialogVisible=true">添加参数</el-button>
                    <!--动态参数表格-->
                    <el-table :data="manyTableData" border stripe>
                        <!--展开行-->
                        <el-table-column type="expand">
                              <template slot-scope="scope">
                                  <!--循环渲染tag标签-->
                                   <el-tag v-for="(item,i) in scope.row.attr_vals" :key="i" closable @close="handleClose(i,scope.row)">{{item}}</el-tag>
                                  <!--按钮和输入框转换-->
                                  <el-input class="input-new-tag"
                                          v-if="scope.row.inputVisible"
                                          v-model="scope.row.inputValue"
                                          ref="saveTagInput"
                                          size="small"
                                          @keyup.enter.native="handleInputConfirm(scope.row)"
                                          @blur="handleInputConfirm(scope.row)"
                                  >
                                  </el-input>
                                  <el-button v-else class="button-new-tag" size="small" @click="showInput(scope.row)">+ New Tag</el-button>


                              </template>
                        </el-table-column>
                         <!--索引列-->
                        <el-table-column label="#" type="index"></el-table-column>
                        <el-table-column label="参数名称" prop="attr_name"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                 <el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.attr_id)">编辑</el-button>
                                <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeParams(scope.row.attr_id)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <!--添加静态属性列表-->
                <el-tab-pane label="静态属性" name="only">
                    <!--添加属性按钮-->
                    <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addDialogVisible=true">添加属性</el-button>
                    <!--静态参数表格-->
                    <el-table :data="onlyTableData" border stripe>
                        <!--展开行-->
                        <el-table-column type="expand">
                            <template slot-scope="scope">
                                <!--循环渲染tag标签-->
                                <el-tag v-for="(item,i) in scope.row.attr_vals" :key="i" closable @close="handleClose(i,scope.row)">{{item}}</el-tag>
                                <!--按钮和输入框转换-->
                                <el-input class="input-new-tag"
                                          v-if="scope.row.inputVisible"
                                          v-model="scope.row.inputValue"
                                          ref="saveTagInput"
                                          size="small"
                                          @keyup.enter.native="handleInputConfirm(scope.row)"
                                          @blur="handleInputConfirm(scope.row)"
                                >
                                </el-input>
                                <el-button v-else class="button-new-tag" size="small" @click="showInput(scope.row)">+ New Tag</el-button>
                            </template>

                        </el-table-column>
                        <!--索引列-->
                        <el-table-column label="#" type="index"></el-table-column>
                        <el-table-column label="属性名称" prop="attr_name"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.attr_id)">编辑</el-button>
                                <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeParams(scope.row.attr_id)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </el-card>

        <!--添加参数的对话框-->
        <el-dialog
                :title="'添加'+ titleText"
                :visible.sync="addDialogVisible"
                width="50%" @close="addDialogClosed">
                <!--添加参数表单-->
                <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
                     <el-form-item :label="titleText" prop="attr_name">
                         <el-input v-model="addForm.attr_name"></el-input>
                     </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="addDialogVisible = false">取 消</el-button>
                     <el-button type="primary" @click="addParams">确 定</el-button>
              </span>
        </el-dialog>

        <!--修改参数的对话框-->
        <el-dialog
                :title="'修改'+ titleText"
                :visible.sync="editDialogVisible"
                width="50%" @close="editDialogClosed">
            <!--添加参数表单-->
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
                <el-form-item :label="titleText" prop="attr_name">
                    <el-input v-model="editForm.attr_name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialogVisible = false">取 消</el-button>
                     <el-button type="primary" @click="editParams">确 定</el-button>
              </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
      data(){
          return{
              //商品分类列表
                cateList:[],
              //级联选择框的配置对象
              cateProps:{
                  value:'cat_id',
                  label:'cat_name',
                  children:'children',
                  expandTrigger: 'hover'
              },
              //级联选择框双向绑定数组
              selectedCateKeys:[],
              //被激活的标签名称
              activeName:'many',
              //动态属性参数
              manyTableData:[],
              //静态属性参数
              onlyTableData:[],
              //添加参数对话框显示与隐藏
              addDialogVisible:false,
              //添加参数表单数据
              addForm:{

              },
              //添加参数校验规则
              addFormRules:{
                    attr_name:[
                        {required:true,message:'请输入参数名称',trigger:'blur'}
                    ]
              },
              //修改参数对话框显示与隐藏
              editDialogVisible:false,
              //修改参数表单数据
              editForm:{

              },
              //修改参数校验规则
              editFormRules:{
                  attr_name:[
                      {required:true,message:'请输入参数名称',trigger:'blur'}
                  ]
              },
              //控制按钮的转换形式
              inputVisible:false,
              //文本框输入的内容
              inputValue:''
          }
      },
        created() {
             this.getCateList()
        }
        ,
        methods:{
          async getCateList(){
              /* const {data:res} = await this.$http.get('categories')
               if(res.meta.status!==200){
                   this.$message.error('获取参数分类列表失败!')
               }
               this.cateList = this.data*/
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
                  }
             ]
          },
            //级联选择框发生变化执行函数
           handleChange(){
              this.getParamsData()
            },

            //tag标签点击
            handleTagClick(){
                console.log(this.activeName)
                this.getParamsData()
            },

            //获取参数列表
            async getParamsData(){
                //选中的不是三级分类
                if(this.selectedCateKeys.length!==3){
                    this.selectedCateKeys = []
                    this.manyTableData = []
                    this.onlyTableData = []
                    return
                }
                console.log(this.selectedCateKeys)

                //根据所选分类id 和当前所处面板 获取对应参数

                /*const {data:res} = await
                this.$http.get('categories/${this.cateId}/attributes',
                    {params:{sel:this.activeName}})
                if(res.meta.status!==200){
                    this.$message.error('获取参数列表失败!')
                }*/

                //字符串转成数组
                /*res.data.forEach(item =>{
                   //为空分割会分割出一个,所以需要判断
                     item.attr_vals = item.attr_vals?item.attr_vals.split(' '):[]
                     //控制文本框的显示与隐藏
                     item.inputVisible = false
                     item.inputValue=''
                })*/

                /*if(this.activeName === 'many'){
                    this.manyTableData = res.data
                }else{
                    this.onlyTableData = res.data
                }*/


                if(this.activeName === 'many'){
                    this.manyTableData = [{
                        "attr_id":1,
                        "attr_name":"cpu",
                        "cat_id":22,
                        "attr_sel":"only",
                        "attr_write":"manual",
                        "attr_vals":["49寸4k超薄曲面","人工智能,55寸4k观影曲面","30核HDR,55寸4k超薄全曲面","人工智能,65寸4k超薄曲面"],
                        "inputVisible":false,//模拟假数据而已 实际后端没有传递过来这个属性
                        "inputValue":''
                    }
                    ]
                }else{
                    this.onlyTableData = [{
                        "attr_id":2,
                        "attr_name":"主体-商品名称",
                        "cat_id":22,
                        "attr_sel":"only",
                        "attr_write":"manual",
                        "attr_vals":["50寸4k超薄曲面","人工智能,55寸4k观影曲面","30核HDR,55寸4k超薄全曲面","人工智能,65寸4k超薄曲面"],
                        "inputVisible":false,
                        "inputValue":''
                    },{
                        "attr_id":3,
                        "attr_name":"显示-曲面",
                        "cat_id":22,
                        "attr_sel":"only",
                        "attr_write":"manual",
                        "attr_vals":["51寸4k超薄曲面","人工智能,55寸4k观影曲面","30核HDR,55寸4k超薄全曲面","人工智能,65寸4k超薄曲面"],
                        "inputVisible":false,
                        "inputValue":''
                    },
                        {
                            "attr_id":4,
                            "attr_name":"系统-智能电视",
                            "cat_id":22,
                            "attr_sel":"only",
                            "attr_write":"manual",
                            "attr_vals":["52寸4k超薄曲面","人工智能,55寸4k观影曲面","30核HDR,55寸4k超薄全曲面","人工智能,65寸4k超薄曲面"],
                            "inputVisible":false,
                            "inputValue":''
                        }

                    ]
                }


            },
            //监听添加对话框的关闭事件 重置添加表单
            addDialogClosed(){
              this.$refs.addFormRef.resetFields()
            },

            //点击按钮 添加参数
            addParams(){
                 this.$refs.addFormRef.validate(async valid =>{
                     if(!valid)return
                     /*const {data:data} = await this.$http.post('categories/${this.cateId}/attributes',{
                         attr_name:this.addForm.attr_name,
                         attr_sel:this.activeName
                     })

                     if(data.meta.status!==201){
                         this.$message.error('添加参数失败!')
                     }*/
                     this.$message.success('添加参数成功!')
                     this.addDialogVisible = false
                     this.getParamsData()
                 })
            },
            //点击编辑 出现修改对话框
            async showEditDialog(attr_id){
              //查询当前参数的信息
              /*const {data:res} = await this.$http.get('categories/${this.cateId}/attributes/${attr_id}',
                  {params:{attr_sel:this.activeName}})

                if(res.meta.status!==200){
                    return this.$message.error('获取参数信息失败!')
                }
                this.editForm = res.data*/
              this.editDialogVisible = true
            },
            //重置修改的表单
            editDialogClosed(){
                this.$refs.editFormRef.resetFields()
            },
            //点击修改按钮 提交
            editParams(){
               this.$refs.editFormRef.validate(async valid =>{
                   /*const {data:data} = await this.$http.post('categories/${this.cateId}/attributes/${this.editForm.attr_id}',{
                         attr_name:this.editForm.attr_name,
                         attr_sel:this.activeName
                     })

                     if(data.meta.status!==201){
                        return this.$message.error('修改参数失败!')
                     }*/
                   this.$message.success('修改参数成功!')
                   this.editDialogVisible = false
                   this.getParamsData()
               })
            },
            //根据id删除参数 提交
            async removeParams(attr_id){
               const confirmResult = await this.$confirm('此操作将永久删除该参数,是否继续?','提示',{
                   confirmButtonText:'确定',
                   cancelButtonText:'取消',
                   type:'warning'
               }).catch(err => err)

                //用户取消删除
                if(confirmResult !=='confirm'){
                    return this.$message.info('已取消删除！')
                }

                //删除业务
                /*const {data:res} = await this.$http.delete('categories/${this.cateId}/attributes/${attr_id}')
                if(res.meta.status!==200){
                    return this.$message.error('删除参数失败!')
                }*/

                this.$message.success('删除参数成功!')
                this.getParamsData()
            },
            //文本框失去焦点 或者按下的enter键
            async handleInputConfirm(row){
               if(row.inputValue.trim().length === 0){
                   row.inputValue = ''
                   row.inputVisible = false
                   return
               }
               //不为空,做后续处理
                row.attr_vals.push(row.inputValue.trim())
                row.inputValue = ''
                row.inputVisible = false

                this.saveAttrVals(row)

            },
            //点击按钮 显示文本输入框
            showInput(row){
              row.inputVisible = true
                //让文本框自动获取焦点
                //$nextTick方法的作用,就是当页面上元素被重新渲染之后,才会指定回调函数中的代码
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
            //删除对应的参数可选项
            handleClose(i,row){
                //删除某一项
                row.attr_vals.splice(i,1)
                this.saveAttrVals(row)
            },
            //将对attr_vals的操作,保存到数据库中
            async saveAttrVals(row){

                //发请请求,保存这次操作
                /*const {data:res} = await this.$http.put('categories/${this.cateId}/attributes/${row.attr_id}',{
                    attr_name:row.attr_name,
                    attr_sel:row.attr_sel,
                    attr_vals:row.attr_vals.join(' ')
                })

                if(res.meta.status!==200){
                    return this.$message.error('修改参数项失败!')
                }*/
                this.$message.success('修改参数项成功!')
            }
        },
        //计算属性
        computed:{
          //如果按钮需要被禁用 返回true 否则返回false
              isBtnDisabled(){
                   if(this.selectedCateKeys.length!==3){
                       return true
                   }
                   return false
              },
            //当前选中三级分类Id
            cateId(){
                  if(this.selectedCateKeys.length === 3){
                      return this.selectedCateKeys[2];
                  }
                  return  null;
            },
            titleText(){
                  if(this.activeName === 'many'){
                      return '动态参数'
                  }
                  return '静态属性'
            }
        }
    }
</script>

<style lang="less" scoped>
         .cat_opt{
             margin: 15px 0px;
         }

         .el-tag{
             margin: 10px;
         }

         .input-new-tag{
             width: 120px;
         }
</style>
