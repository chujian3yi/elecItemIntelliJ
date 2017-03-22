var menu = {
    setting: {
        isSimpleData: true,
        treeNodeKey: "mid",
        treeNodeParentKey: "pid",
        showLine: true,
        root: {
            isRoot: true,
            nodes: []
        }
    },
    loadMenuTree:function(){
        $.post("elecMenuAction_showMenu.do",null,function(data){
            //data返回json数据，封装List<ElecPopedom>
            $("#menuTree").zTree(menu.setting, data.modelList);
        })

    }
};

$().ready(function(){
    menu.loadMenuTree();
});
