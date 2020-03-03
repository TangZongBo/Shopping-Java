<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加商品</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图-->
        <el-card>
            <!--提示区域-->
              <el-alert title="添加商品信息" type="info" center show-icon :closable="false"></el-alert>
            <!--导航条-->
            <el-steps :space="200" :active="activeIndex - 0" finish-status="success" align-center>
                <el-step title="基本信息"></el-step>
                <el-step title="商品参数"></el-step>
                <el-step title="商品属性"></el-step>
                <el-step title="商品图片"></el-step>
                <el-step title="商品内容"></el-step>
                <el-step title="完成"></el-step>
            </el-steps>

            <!--表单-->
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px" label-position="top">
            <!--tab栏区域-->
             <el-tabs v-model="activeIndex" :tab-position="'left'" :before-leave="beforeTabLeave" @tab-click="tabClicked">
                  <el-tab-pane label="基本信息" name="0">
                       <el-form-item label="商品名称" prop="goods_name">
                            <el-input v-model="addForm.goods_name"></el-input>
                       </el-form-item>
                      <el-form-item label="商品价格" prop="goods_price">
                          <el-input v-model="addForm.goods_price" type="number"></el-input>
                      </el-form-item>
                      <el-form-item label="商品重量" prop="goods_weight">
                          <el-input v-model="addForm.goods_weight" type="number"></el-input>
                      </el-form-item>
                      <el-form-item label="商品数量" prop="goods_number">
                          <el-input v-model="addForm.goods_number" type="number"></el-input>
                      </el-form-item>
                      <el-form-item label="商品分类" prop="goods_cat">
                          <!--级联选择器-->
                          <el-cascader
                                  v-model="addForm.goods_cat"
                                  :options="cateList"
                                  :props="cateProps"
                                  @change="handleChange"></el-cascader>
                      </el-form-item>
                  </el-tab-pane>

                 <el-tab-pane label="商品参数" name="1">
                     <!--渲染item项-->
                     <el-form-item :label="item.attr_name" v-for="item in manyTableData" :key="item.attr_id">
                         <!--复选框组-->
                         <el-checkbox-group v-model="item.attr_vals">
                             <el-checkbox :label="item2" v-for="(item2,i) in item.attr_vals" :key="i" border></el-checkbox>
                         </el-checkbox-group>
                     </el-form-item>
                 </el-tab-pane>
                 <el-tab-pane label="商品属性" name="2">
                     <el-form-item :label="item.attr_name" v-for="item in onlyTableData" :key="item.attr_id">
                         <el-input v-model="item.attr_vals"></el-input>
                     </el-form-item>
                 </el-tab-pane>
                 <el-tab-pane label="商品图片" name="3">
                     <!--上传图片 action图片表示后台上传api地址 list-type只能预览效果-->
                     <el-upload
                             :action="uploadURL"
                             :on-preview="handlePreview"
                             :on-remove="handleRemove"
                             list-type="picture" :headers="headerObj" :on-success="handleSuccess">
                         <el-button size="small" type="primary">点击上传</el-button>
                     </el-upload>
                 </el-tab-pane>
                 <el-tab-pane label="商品内容" name="4">
                      <!--富文本编辑器组件-->
                     <quill-editor v-model="addForm.goods_introduce"></quill-editor>

                     <!--添加商品按钮-->
                      <el-button type="primary" class="addBtn" @click="addGood">添加商品</el-button>
                 </el-tab-pane>
             </el-tabs>
            </el-form>
        </el-card>

         <!--图片预览-->
                <el-dialog title="图片预览" :visible.sync="previewVisible" width="50%">
                    <img :src="previewPath" alt="" class="previewImg"/>
                </el-dialog>
    </div>
</template>

<script>
     import _ from 'lodash'

    export default {
        data(){
            return{
               activeIndex:'0',
                //添加商品表单对象
                addForm:{
                    goods_name:'',
                    goods_price:0,
                    goods_weight:0,
                    goods_number:0,
                    //商品所属的分类数组
                    goods_cat:[],
                    //图片的数组
                    pics:[],
                    //商品的详情描述
                    goods_introduce:'',
                    attrs:[]
                },
                //商品分类列表
                cateList:[],
                cateProps: {
                    label: 'cat_name',
                    value:'cat_id',
                    children:'children',
                    expandTrigger: 'hover',
                    checkStrictly: true // 可以选择任意一级 默认为false
                },
                //添加商品验证规则
                addFormRules:{
                    goods_name:[{
                            required:true,
                            message:'请输入商品名称',
                            trigger:'blur'
                        }],
                    goods_price:[{
                        required:true,
                        message:'请输入商品价格',
                        trigger:'blur'
                    }],
                    goods_weight:[{
                        required:true,
                        message:'请输入商品重量',
                        trigger:'blur'
                    }],
                    goods_number:[{
                        required:true,
                        message:'请输入商品数量',
                        trigger:'blur'
                    }],
                    goods_cat:[{
                            required:true,
                            message:'请选择商品分类',
                            trigger:'blur'
                        }]
                },
                //动态参数列表数据
                manyTableData:[],
                //静态属性列表数据
                onlyTableData:[],
                //上传图片地址
                uploadURL:'http://localhost:8181/upload',
                //图片上传的请求头对象
                headerObj:{Authorization:window.sessionStorage.getItem('token')},
                previewPath:'',
                previewVisible:false
            }
        },
       created() {
              //获取所有商品分类数据
              this.getCateList()
       },
        methods:{
             async getCateList(){
                 /*const {data:res} = await this.$http.get('categories')
                 if(res.meta.status!==200){
                     return this.$message.error('获取商品分类失败!')
                 }
                 this.cateList=res.data*/
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


             },

            //级联选择器器发生变化触发
            handleChange(){
                 console.log(this.addForm.goods_cat)
                //只能选择三级菜单
                if(this.addForm.goods_cat.length!==3){
                    this.addForm.goods_cat = []
                }
            },

            beforeTabLeave(activeName,oldActiveName){

                 if(oldActiveName==='0'&&this.addForm.goods_cat.length!==3){
                     this.$message.error('请先选择商品分类!')
                     return false
                 }
            },

            async tabClicked(){
                 if(this.activeIndex==='1'){
                      /*const {data:res} = await this.$http.get(`categories/${this.cateId}/attributes`,
                          {params:{sel:'many'}
                          }
                          )
                     if(res.meta.status !==200){
                         return this.$message.error('获取参数列表失败!')
                     }


                     res.data.forEach(item =>{
                          item.attr_vals =
                              item.attr_vals.length===0?[]:item.attr_vals.split(' ') 后台传给来的是字符串
                      })
                      this.manyTableData = res.data
                     */


                      this.manyTableData = [{
                          "attr_id":1,
                          "attr_name":"版式",
                          "cat_id":22,
                          "attr_sel":"only",
                          "attr_write":"manual",
                          "attr_vals":["49寸4k超薄曲面","人工智能,55寸4k观影曲面","30核HDR,55寸4k超薄全曲面","人工智能,65寸4k超薄曲面","人工智能","人工智能xxx"],
                          "inputVisible":false,//模拟假数据而已 实际后端没有传递过来这个属性
                          "inputValue":''
                      },{
                          "attr_id":2,
                          "attr_name":"颜色",
                          "cat_id":22,
                          "attr_sel":"only",
                          "attr_write":"manual",
                          "attr_vals":["黄色","黑色"],
                          "inputVisible":false,//模拟假数据而已 实际后端没有传递过来这个属性
                          "inputValue":''
                      }]


                     console.log(this.manyTableData)
                 }else if(this.activeIndex === '2'){
                     /*const {data:res} = await this.$http.get(`categories/${this.cateId}/attributes`,
                         {params:{sel:'only'}
                         }
                     )
                     if(res.meta.status !==200){
                         return this.$message.error('获取静态属性失败!')
                     }
                    console.log(res.data)
                     this.onlyTableData = res.data*/

                     this.onlyTableData = [{
                         "attr_id":2,
                         "attr_name":"主体-商品名称",
                         "cat_id":22,
                         "attr_sel":"only",
                         "attr_write":"manual",
                         "attr_vals":"TCL电视 55A950C",
                         "inputVisible":false,
                         "inputValue":''
                     },{
                         "attr_id":3,
                         "attr_name":"显示-曲面",
                         "cat_id":22,
                         "attr_sel":"only",
                         "attr_write":"manual",
                         "attr_vals":"是",
                         "inputVisible":false,
                         "inputValue":''
                     },
                         {
                             "attr_id":4,
                             "attr_name":"系统-智能电视",
                             "cat_id":22,
                             "attr_sel":"only",
                             "attr_write":"manual",
                             "attr_vals":"智能电视",
                             "inputVisible":false,
                             "inputValue":''
                         }

                     ]
                 }
            },
            //处理图片预览效果
            handlePreview(file){
                console.log(file)
                /*this.previewPath = file.response.data.url
                this.previewVisible = true*/
            },
            //处理移除图片的操作
            handleRemove(file){
                  console.log(file)
                  //1.获取将要删除的图片的临时路径
                 /*const filePath = file.response.data.tmp_path
                 //2.从pics数组中  找到这个图片对应的索引值
                 const i = this.addForm.pics.findIndex(x =>x.pic === filePath)
                //3.调用数组的splice方法 把图片信息对象 从pics数组中移除
                this.addForm.pics.splice(i,1)
                console.log(this.addForm)*/
            },
            //监听图片上传成功
            handleSuccess(response){
                console.log(response)
                //1.拼接得到一个图片信息对象
                /*const picInfo = {pic:response.data.tmp_path}
                //2.将图片信息,push到pics数组中
                this.addForm.pics.push(picInfo)
                console.log(this.addForm)*/
                this.addForm.pics.push({pic:'111.jpg'})
            },
            //添加商品按钮
            addGood(){
                 console.log(this.addForm)
                this.$refs.addFormRef.validate(async valid =>{
                         if(!valid){
                            return  this.$message.error('请填写必要的表单项!')
                         }
                         //执行添加
                         //lodash cloneDeep(obj)
                          const form = _.cloneDeep(this.addForm)
                          form.goods_cat = form.goods_cat.join(',')
                        //处理动态参数
                        this.manyTableData.forEach(item =>{
                            const newItem = {
                                attr_id:item.attr_id,
                                attr_value:item.attr_vals.join(' ')
                            }
                             this.addForm.attrs.push(newItem)
                        })
                       //处理静态属性
                       this.onlyTableData.forEach(item =>{
                           const newItem = {
                               attr_id:item.attr_id,
                               attr_value:item.attr_vals
                           }
                           this.addForm.attrs.push(newItem)
                       })

                       form.attrs = this.addForm.attrs

                       /*const {data:res} = await this.$http.post('goods',form)
                       if(res.meta.status!==201){
                           return this.$message.error('添加商品失败!')
                       }*/
                         this.$message.success('添加商品成功!')
                        this.$router.push('/goods')
                })
            }
        },
        //计算属性
        computed:{
              cateId(){
                  if(this.addForm.goods_cat.length===3){
                      return this.addForm.goods_cat[2]
                  }
                   return null
              }
        }
    }
</script>

<style lang="less" scoped>
       .el-checkbox{
           margin: 0 10px 0 0 !important;
       }
       .previewImg{
           width: 100%;
       }
        .addBtn{
            margin-top: 15px;
        }
</style>
