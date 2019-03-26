/**
 * 解析器,首先会根据自定义的jquery控件的名称,去页面上去查找使用了这个控件名称定义了类的元素,如:控件"linkbutton"将会去页面上查找$(".linkbutton"),查找完之后,就会去加载对应的控件模块。
 * 每个模块加载的时候都会在jquery的fn上扩展了一控件名字作为名称的方法属性,如"linkbutton"控件注册了"$.fn.linkbutton = function(options){...}"
 * 控件加载全部加载完成之后就针对每一个html元素调用控件的构造方法,于是一个控件就在页面上渲染完成了. *
 *
 */
(function($){
   $.parser = {
      auto: true,
      onComplete: function(context){},
      //下面是支持的插件的名字,例如linkbutton代表".ylf-linkbutton"
      plugins:[],
      parse: function(context){
         //解析使用ylf class定义的html元素
         //存储查找到引用了指定名称控件的名字和对应的元素,如:[{name:"linkbutton",jq:$(".ylf-linkbutton")]
         for(var i=0; i<$.parser.plugins.length; i++){
            //控件的名字,如:linkbutton
            var name = $.parser.plugins[i];
            //选中使用的控件的html元素集合,如:$(".ylf-linkbutton")
            var r = $('.ylf-' + name, context);
            if (r.length){
               if (r[name]){
                  //如果已经加载过当前的控件了,这里应该是每个控件加载完成之后,都会在jquery的fn上扩展一个以改控件名称命名的属性
                  r[name]();
               } else {
                  
               }
            }
         }
      },
      //转换data-options中为对象
      parseOptions: function(target, properties){
          var t = $(target);
          var options = {};
          
          var s = $.trim(t.attr('data-options'));
          if (s){
              if (s.substring(0, 1) != '{'){
                  s = '{' + s + '}';
              }
              options = (new Function('return ' + s))();
          }
          $.map(['width','height','left','top','minWidth','maxWidth','minHeight','maxHeight'], function(p){
              var pv = $.trim(target.style[p] || '');
              if (pv){
                  if (pv.indexOf('%') == -1){
                      pv = parseInt(pv) || undefined;
                  }
                  options[p] = pv;
              }
          });
              
          if (properties){
              var opts = {};
              for(var i=0; i<properties.length; i++){
                  var pp = properties[i];
                  if (typeof pp == 'string'){
                      opts[pp] = t.attr(pp);
                  } else {
                      for(var name in pp){
                          var type = pp[name];
                          if (type == 'boolean'){
                              opts[name] = t.attr(name) ? (t.attr(name) == 'true') : undefined;
                          } else if (type == 'number'){
                              opts[name] = t.attr(name)=='0' ? 0 : parseFloat(t.attr(name)) || undefined;
                          }
                      }
                  }
              }
              $.extend(options, opts);
          }
          return options;
      }
   };
   $(function(){
      //执行方法$.parser.parse()
      if ($.parser.auto){
         //如果已经加载过easyloader.js脚本的话,下面的函数是不会被执行的
         $.parser.parse();
      }
   });
})(jQuery);