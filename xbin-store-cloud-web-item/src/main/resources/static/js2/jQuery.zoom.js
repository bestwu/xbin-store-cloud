/*!Name: jQuery.zoom.js
 * Date: 2017-2-14 17:14:18 */
!function(o){o.fn.jqueryzoom=function(i){var t={xzoom:200,yzoom:200,offset:10,position:"right",lens:1,preload:1};i&&o.extend(t,i);var s="";o(this).hover(function(){function i(o){this.x=o.pageX,this.y=o.pageY}var e=o(this).offset().left,d=o(this).offset().top;if(0!=o(this).find("img").length){var m=o(this).find("img").get(0).offsetWidth,v=o(this).find("img").get(0).offsetHeight;s=o(this).find("img").attr("alt");var h=o(this).find("img").attr("jqimg");o(this).find("img").attr("alt",""),0==o("div.zoomdiv").get().length&&(o(this).after("<div class='zoomdiv'><img class='bigimg' src='"+h+"'/></div>"),o(this).append("<div class='jqZoomPup'>&nbsp;</div>")),o("div.zoomdiv").width(t.xzoom),o("div.zoomdiv").height(t.yzoom),o("div.zoomdiv").show(),t.lens||o(this).css("cursor","crosshair"),o(document.body).mousemove(function(s){mouse=new i(s);var h=o(".bigimg").get(0).offsetWidth,n=o(".bigimg").get(0).offsetHeight,u="x",l="y";if(isNaN(l)|isNaN(u)){var l=h/m,u=n/v;o("div.jqZoomPup").width(t.xzoom/(1*l)),o("div.jqZoomPup").height(t.yzoom/(1*u)),t.lens&&o("div.jqZoomPup").css("visibility","visible")}xpos=mouse.x-o("div.jqZoomPup").width()/2-e,ypos=mouse.y-o("div.jqZoomPup").height()/2-d,t.lens&&(xpos=mouse.x-o("div.jqZoomPup").width()/2<e?0:mouse.x+o("div.jqZoomPup").width()/2>m+e?m-o("div.jqZoomPup").width()-2:xpos,ypos=mouse.y-o("div.jqZoomPup").height()/2<d?0:mouse.y+o("div.jqZoomPup").height()/2>v+d?v-o("div.jqZoomPup").height()-2:ypos),t.lens&&o("div.jqZoomPup").css({top:ypos,left:xpos}),scrolly=ypos,o("div.zoomdiv").get(0).scrollTop=scrolly*u,scrollx=xpos,o("div.zoomdiv").get(0).scrollLeft=scrollx*l})}},function(){o(this).children("img").attr("alt",s),o(document.body).unbind("mousemove"),t.lens&&o("div.jqZoomPup").remove(),o("div.zoomdiv").remove()}),count=0,t.preload&&(o("body").append("<div style='display:none;' class='jqPreload"+count+"'>360buy</div>"),o(this).each(function(){var i=o(this).children("img").attr("jqimg"),t=jQuery("div.jqPreload"+count).html();jQuery("div.jqPreload"+count).html(t+'<img src="'+i+'">')}))}}(jQuery);