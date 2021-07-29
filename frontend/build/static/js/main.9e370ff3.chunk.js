(this["webpackJsonpschedule-cleaner"]=this["webpackJsonpschedule-cleaner"]||[]).push([[0],{99:function(e,t,a){"use strict";a.r(t);var c=a(0),n=a.n(c),r=a(11),s=a.n(r),i=a(58),l=a(19),o=a(151),d=a(155),j=a(152),u=a(160),b=a(101),h=a(161),x=a(156),O=a(133),p=a(55),m=a(46),f=a(131),g=Object(f.a)((function(e){return{header:{backgroundColor:e.palette.background.paper,padding:e.spacing(2,0,2),display:"flex",justifyContent:"center",alignItems:"center"},headerImg:{width:150,backgroundColor:e.palette.primary.dark,borderRadius:20},headerText:Object(m.a)({maxWidth:410,minWidth:410},e.breakpoints.down("xs"),{textAlign:"center",minWidth:"auto"})}})),v=a(3);function y(){var e=g();return Object(v.jsx)(d.a,{p:2,children:Object(v.jsxs)(O.a,{className:e.header,container:!0,spacing:4,children:[Object(v.jsx)(O.a,{item:!0,children:Object(v.jsx)("img",{className:e.headerImg,src:"assets/scheduleCleaner.svg",alt:"Schedule Cleaner"})}),Object(v.jsx)(O.a,{item:!0,className:e.headerText,children:Object(v.jsx)(p.a,{variant:"h5",color:"textSecondary",children:"Filter unnecessary junk from your NAK Schedule."})})]})})}var C=a(148),k=a(141),I=a(154),S=a(149),R=a(150),N=a(25),T=a(135),w=a(136),A=a(137),E=a(138),F=a(102),W=a(142),B=a(143),L=a(158),U=a(76);function z(){return Object(v.jsxs)(U.a,{children:[Object(v.jsx)("path",{d:"M0,0h24 M24,24H0",fill:"none"}),Object(v.jsx)("path",{d:"M4.25,5.61C6.27,8.2,10,13,10,13v6c0,0.55,0.45,1,1,1h2c0.55,0,1-0.45,1-1v-6c0,0,3.72-4.8,5.74-7.39 C20.25,4.95,19.78,4,18.95,4H5.04C4.21,4,3.74,4.95,4.25,5.61z"}),Object(v.jsx)("path",{d:"M0,0h24v24H0V0z",fill:"none"})]})}var K=a(140),P=a(144),H=Object(f.a)((function(e){return{divider:{margin:"10px 0px"},listInput:{marginRight:10},listSubheader:{display:"flex",justifyContent:"space-between",alignItems:"center"},listItemText:{color:"white",cursor:"default"},enabledButton:{fontWeight:"bold"},disabledButton:{fontWeight:"normal"}}}));function M(e){var t=H();return Object(v.jsx)(T.a,{subheader:Object(v.jsxs)(w.a,{className:t.listSubheader,children:[Object(v.jsx)(A.a,{children:Object(v.jsx)(z,{})}),Object(v.jsx)(E.a,{className:t.listItemText,children:"Exclude events"}),Object(v.jsx)(u.a,{title:"Exclude specific events based on phrases it must ALL contain",arrow:!0,children:Object(v.jsx)(b.a,{variant:"outlined",startIcon:Object(v.jsx)(K.a,{}),onClick:function(t){return e.setExclude([].concat(Object(N.a)(e.exclude),[{id:Object(L.a)(),text:""}]))},children:"Add"})})]}),children:e.exclude.map((function(a,c){return Object(v.jsx)(n.a.Fragment,{children:Object(v.jsxs)(F.a,{children:[Object(v.jsx)(u.a,{title:"Phrases can be separated by using ; (semicolon)",arrow:!0,children:Object(v.jsx)(I.a,{margin:"dense",variant:"outlined",value:a.text,label:"Text",fullWidth:!0,className:t.listInput,onChange:function(t){var a=Object(N.a)(e.exclude);a[c].text=t.target.value,e.setExclude(a)}})}),Object(v.jsx)(W.a,{children:Object(v.jsx)(B.a,{edge:"end",onClick:function(t){var a=Object(N.a)(e.exclude);a.splice(c,1),e.setExclude(a)},children:Object(v.jsx)(P.a,{})})})]})},a.id)}))})}var _=/([a-zA-Z][0-9]{2}[a-zA-Z])/,q=/[0-9]/,D={centuria:function(e){return _.test(e)},semester:function(e){return q.test(e)},replaceText:function(e){return!e.includes(";")}},G=a(145);function J(e){var t=H();return Object(v.jsx)(T.a,{subheader:Object(v.jsxs)(w.a,{className:t.listSubheader,children:[Object(v.jsx)(A.a,{children:Object(v.jsx)(G.a,{})}),Object(v.jsx)(E.a,{className:t.listItemText,children:"Replace text"}),Object(v.jsx)(u.a,{title:"Replace the text of an event with another text",arrow:!0,children:Object(v.jsx)(b.a,{variant:"outlined",startIcon:Object(v.jsx)(K.a,{}),onClick:function(t){return e.setReplace([].concat(Object(N.a)(e.replace),[{before:"",after:"",id:Object(L.a)()}]))},children:"Add"})})]}),children:e.replace.map((function(a,c){return Object(v.jsx)(n.a.Fragment,{children:Object(v.jsxs)(F.a,{children:[Object(v.jsx)(I.a,{margin:"dense",variant:"outlined",label:"Before",error:!D.replaceText(a.before),onChange:function(t){var a=Object(N.a)(e.replace);a[c].before=t.target.value,e.setReplace(a)},children:a.before}),Object(v.jsx)("div",{style:{marginRight:10}}),Object(v.jsx)(I.a,{margin:"dense",variant:"outlined",label:"After",className:t.listInput,error:!D.replaceText(a.after),onChange:function(t){var a=Object(N.a)(e.replace);a[c].after=t.target.value,e.setReplace(a)},children:a.after}),Object(v.jsx)(W.a,{children:Object(v.jsx)(B.a,{edge:"end",onClick:function(t){var a=Object(N.a)(e.replace);a.splice(c,1),e.setReplace(a)},children:Object(v.jsx)(P.a,{})})})]})},a.id)}))})}var Z=a(157),V=a(146);function Q(e){var t=H();return Object(v.jsx)(T.a,{children:Object(v.jsxs)(F.a,{children:[Object(v.jsx)(A.a,{children:Object(v.jsx)(V.a,{})}),Object(v.jsx)(E.a,{className:t.listItemText,children:"Room as location"}),Object(v.jsx)(W.a,{children:Object(v.jsx)(u.a,{title:"Set the room as the event's location",arrow:!0,children:Object(v.jsx)(Z.a,{edge:"end",checked:e.fixRoom,color:"primary",onChange:function(t){return e.setFixRoom(t.target.checked)}})})})]})})}var X=a(147);function Y(e){var t=H();return Object(v.jsx)(T.a,{children:Object(v.jsxs)(F.a,{children:[Object(v.jsx)(A.a,{children:Object(v.jsx)(X.a,{})}),Object(v.jsx)(E.a,{className:t.listItemText,children:"Keep module code"}),Object(v.jsx)(W.a,{children:Object(v.jsx)(u.a,{title:"Keep the module code (e.g. W118) in the title",arrow:!0,children:Object(v.jsx)(Z.a,{edge:"end",checked:e.keepCodeInTitle,color:"primary",onChange:function(t){return e.setKeepCodeInTitle(t.target.checked)}})})})]})})}var $=a(54);var ee=[1,2,3,4,5,6,7];function te(e){var t=H(),a=Object(c.useState)(""),n=Object(l.a)(a,2),r=n[0],s=n[1],i=Object(c.useState)(""),o=Object(l.a)(i,2),j=o[0],u=o[1],b=Object(c.useState)([]),h=Object(l.a)(b,2),x=h[0],m=h[1],f=Object(c.useState)([]),g=Object(l.a)(f,2),y=g[0],N=g[1],T=Object(c.useState)(!0),w=Object(l.a)(T,2),A=w[0],E=w[1],F=Object(c.useState)(!1),W=Object(l.a)(F,2),B=W[0],L=W[1];return Object(c.useEffect)((function(){e.setIcsURL(function(e,t,a,c,n,r){if(!D.centuria(e)||!D.semester(t))return null;var s,i=Object($.a)(c);try{for(i.s();!(s=i.n()).done;){var l=s.value;if(!D.replaceText(l.before)||!D.replaceText(l.after))return null}}catch(p){i.e(p)}finally{i.f()}var o=new URL("https://schedule-cleaner.herokuapp.com/cleaned-schedule/".concat(e,"_").concat(t,".ics"));if(a.filter((function(e){return e.text.trim().length>0})).length>0){var d,j=Object($.a)(a);try{for(j.s();!(d=j.n()).done;){var u=d.value;o.searchParams.append("exclude","".concat(u.text))}}catch(p){j.e(p)}finally{j.f()}}var b=c.filter((function(e){return e.before.trim().length>0&&e.after.trim().length>0}));if(b.length>0){var h,x=Object($.a)(b);try{for(x.s();!(h=x.n()).done;){var O=h.value;o.searchParams.append("replace","".concat(O.before,";").concat(O.after))}}catch(p){x.e(p)}finally{x.f()}}return r&&o.searchParams.append("title","keepCode"),n?(o.searchParams.append("location",""),o.toString().slice(0,-1)):o.toString()}(r,j,x,y,A,B)),e.setIcsName("".concat(r,"_").concat(j))}),[r,j,x,y,A,B,e]),Object(v.jsx)(C.a,{maxWidth:"sm",children:Object(v.jsxs)(k.a,{elevation:3,style:{padding:16},children:[Object(v.jsx)(p.a,{align:"center",variant:"h5",color:"textSecondary",children:"Configure your schedule"}),Object(v.jsx)(d.a,{mb:2,mt:1,children:Object(v.jsxs)(O.a,{container:!0,alignItems:"flex-start",spacing:3,children:[Object(v.jsx)(O.a,{item:!0,xs:6,children:Object(v.jsx)(I.a,{autoFocus:!0,margin:"dense",variant:"outlined",label:"Centuria",name:"centuria",fullWidth:!0,required:!0,value:r,error:!D.centuria(r),onChange:function(e){return s(e.target.value)}})}),Object(v.jsx)(O.a,{item:!0,xs:6,children:Object(v.jsx)(I.a,{type:"number",margin:"dense",variant:"outlined",select:!0,label:"Semester",name:"semester",fullWidth:!0,required:!0,value:j,error:!D.semester(j),onChange:function(e){return u(e.target.value.toString())},children:ee.map((function(e){return Object(v.jsx)(S.a,{value:e,children:e},e)}))})})]})}),Object(v.jsx)(R.a,{className:t.divider}),Object(v.jsx)(Q,{fixRoom:A,setFixRoom:E}),Object(v.jsx)(R.a,{className:t.divider}),Object(v.jsx)(Y,{keepCodeInTitle:B,setKeepCodeInTitle:L}),Object(v.jsx)(R.a,{className:t.divider}),Object(v.jsx)(M,{exclude:x,setExclude:m}),Object(v.jsx)(R.a,{className:t.divider}),Object(v.jsx)(J,{replace:y,setReplace:N})]})})}var ae=a(71),ce=a.n(ae);function ne(e,t){var a=document.createElement("a");a.setAttribute("download",t),a.setAttribute("target","_blank"),a.href=e,document.body.appendChild(a),a.click(),a.remove()}var re=function(){var e=H(),t=Object(c.useState)(null),a=Object(l.a)(t,2),n=a[0],r=a[1],s=Object(c.useState)(""),O=Object(l.a)(s,2),p=O[0],m=O[1],f=Object(c.useState)({open:!1,status:!1,message:""}),g=Object(l.a)(f,2),C=g[0],k=g[1];return Object(v.jsxs)(v.Fragment,{children:[Object(v.jsx)(o.a,{}),Object(v.jsxs)("main",{children:[Object(v.jsx)(y,{}),Object(v.jsx)(d.a,{display:"flex",justifyContent:"center",alignItems:"center",m:2,children:Object(v.jsxs)(j.a,{children:[Object(v.jsx)(u.a,{title:"Copies the URL into your clipboard",arrow:!0,children:Object(v.jsx)(b.a,{classes:{root:e.enabledButton,disabled:e.disabledButton},color:"primary",disabled:null===n,onClick:function(){return function(e){ce()(e)?k({open:!0,status:!0,message:"Copied URL to the clipboard."}):k({open:!0,status:!1,message:"Failed to copy URL to the clipboard!"})}(n||"")},children:"Generate URL"})}),Object(v.jsx)(b.a,{classes:{root:e.enabledButton,disabled:e.disabledButton},color:"primary",disabled:null===n,onClick:ne.bind(null,n||"",p),children:"Download ICS"})]})}),Object(v.jsx)(te,{setIcsURL:r,setIcsName:m}),Object(v.jsx)(d.a,{display:"flex",justifyContent:"center",alignItems:"center",m:3,children:Object(v.jsx)(b.a,{href:"https://github.com/jeff-saupe/ScheduleCleaner#%EF%B8%8F-schedule-cleaner",variant:"outlined",color:"primary",children:"This project on Github"})})]}),Object(v.jsx)(h.a,{anchorOrigin:{vertical:"bottom",horizontal:"right"},open:C.open,onClose:function(){return k(Object(i.a)(Object(i.a)({},C),{},{open:!1}))},autoHideDuration:5e3,children:Object(v.jsx)(x.a,{elevation:6,variant:"filled",severity:C.status?"success":"error",children:C.message})})]})},se=a(73),ie=a(153),le=a(72),oe=a.n(le),de=Object(se.a)({palette:{type:"dark",background:{paper:"#34444c",default:"#263238"},primary:oe.a}});s.a.render(Object(v.jsxs)(ie.a,{theme:de,children:[Object(v.jsx)(re,{}),Object(v.jsx)("app-naktoolsbadge",{"text-color":de.palette.text.primary,"background-color":de.palette.background.paper})]}),document.getElementById("root"))}},[[99,1,2]]]);
//# sourceMappingURL=main.9e370ff3.chunk.js.map