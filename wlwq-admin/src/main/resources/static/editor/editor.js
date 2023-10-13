        var uploadUrl = "/app/upload";

    // //获取富文本内容
    // function GetTinyMceContent(editorId) {
    //     console.log(tinyMCE);
    //     console.log(tinyMCE.activeEditor.getContent());
    //     $("#content").val(tinyMCE.activeEditor.getContent());
    // }

    //加载页
    function loading(status) {
        if (status) {
            const divEle = document.createElement('div')
            const styleEle = document.createElement('style')
            // 底部遮罩样式
            const loadignStyle = '.loading{position: absolute;z-index: 9999;left: 0;top:0;width: 100%;height: 100%;background-color: rgba(0,0,0, .6)}'
            // loading动画样式
            const loadignChildStyle =
                '.loading div{position: absolute;width: 30px;height: 30px;top: 50%;left: 50%;margin: -15px 0 0 -15px;border: 1px solid #fff;border-right: 1px solid transparent;border-radius: 50%;animation: loading 1s linear infinite;}'
            divEle.setAttribute('class', 'loading')
            divEle.innerHTML = '<div></div>'
            styleEle.innerHTML = `${loadignStyle} ${loadignChildStyle} @keyframes loading {to {transform: rotate(360deg)}}`
            document.querySelector('head').append(styleEle)
            document.querySelector('.tox-dialog').append(divEle)
            loadingEle = divEle
        } else {
            // 控制loading的显示与隐藏
            const divEle = document.querySelector('.loading')
            divEle.setAttribute('style', `display:none`)
            setTimeout(function () {
                divEle.remove();
            }, 500)
        }
    }

    //错误提示页面
    function errorHtml(message) {
        const divEle = document.createElement('div')
        const styleEle = document.createElement('style')
        const divText = message
        // 底部遮罩样式
        const loadignStyle = '.loading{position: absolute;z-index: 9999;left: 0;top:0;width: 100%;height: 100%;}'
        // loading动画样式
        const loadignChildStyle =
            '.loading .divs{display: flex;align-items: center;color: #fff;background: rgba(0,0,0,0.6);justify-content: center;padding: 0.5rem 0;margin: 0 30%;border-radius: 0.25rem;top: 41%;position: relative;}'
        const loadignChildOneStyle =
            '.loading .div-one { width: 1rem;height: 1rem;background-color: #000;color: #fff;text-align: center;line-height: 1rem;border-radius: 100%;margin-right: 0.5rem;}'
        const loadignChildTwoStyle =
            '.loading .div-two { line-height: 1rem;}'
        divEle.setAttribute('class', 'loading')
        divEle.innerHTML = `<div class="divs"><div class="div-one">!</div><div class="div-two">${divText}</div></div>`
        styleEle.innerHTML = `${loadignStyle} ${loadignChildStyle} ${loadignChildOneStyle} ${loadignChildTwoStyle}`
        document.querySelector('head').append(styleEle)
        document.querySelector('.tox-dialog').append(divEle)
        loadingEle = divEle
        setTimeout(function () {
            divEle.remove();
        }, 1000)
    }

    function setMytextareaIdName(IdName) {
         var newIdName='#'+IdName;
         console.log(newIdName);
        tinymce.init({
            selector: newIdName,
            //skin:'oxide-dark',
            language: 'zh_CN',
            plugins: 'code kityformula-editor print preview searchreplace autolink directionality visualblocks visualchars fullscreen image link media template code codesample table charmap hr pagebreak nonbreaking anchor insertdatetime advlist lists wordcount imagetools textpattern help emoticons autosave bdmap indent2em autoresize formatpainter axupimgs  powerpaste netpaster',
            toolbar: 'code kityformula-editor | code undo redo restoredraft | cut copy powerpaste pastetext | forecolor backcolor bold italic underline strikethrough link anchor | alignleft aligncenter alignright alignjustify outdent indent | \
  styleselect formatselect fontselect fontsizeselect | bullist numlist | blockquote subscript superscript removeformat | \
  table image media charmap emoticons hr pagebreak insertdatetime print preview | fullscreen | bdmap indent2em lineheight formatpainter axupimgs  netpaster',
            axupimgs_filetype: '.png,.gif,.jpg,.jpeg',
            height: 650, //编辑器高度
            min_height: 400,
            paste_data_images: true,
            /*content_css: [ //可设置编辑区内容展示的css，谨慎使用
                '/static/reset.css',
                '/static/ax.css',
                '/static/css.css',
            ],*/
            fontsize_formats: '12px 14px 16px 18px 24px 36px 48px 56px 72px',
            font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',
            link_list: [{
                title: '预置链接1',
                value: 'http://www.tinymce.com'
            }, {
                title: '预置链接2',
                value: 'http://tinymce.ax-z.cn'
            }],
            image_list: [{
                title: '预置图片1',
                value: 'https://www.tiny.cloud/images/glyph-tinymce@2x.png'
            }, {
                title: '预置图片2',
                value: 'https://www.baidu.com/img/bd_logo1.png'
            }],
            image_class_list: [{
                title: 'None',
                value: ''
            }, {
                title: 'Some class',
                value: 'class-name'
            }],
            powerpaste_word_import: 'propmt', // 参数可以是propmt, merge, clear，效果自行切换对比
            powerpaste_html_import: 'propmt', // propmt, merge, clear
            powerpaste_allow_local_images: true,
            importcss_append: true,
            //自定义文件选择器的回调内容
            file_picker_callback: function(callback, value, meta) {
                if (meta.filetype === 'file') {
                    var fileTypeString = '.pdf, .txt, .zip, .rar, .7z, .doc, .docx, .xls, .xlsx, .ppt, .pptx';
                    var fileTypeText = "上传文件类型错误";
                } else if (meta.filetype === 'image') {
                    var fileTypeString = '.bmp, .png, .tif, .gif, .jpg, .jpeg';
                    var fileTypeText = "上传图片类型错误";
                } else if (meta.filetype === 'media') {
                    var fileTypeString = '.avi, .wmv, .mpg, .mpeg, .mov, .ram, .swf, .flv, .mp4, .mp3, .wma, .avi, .rm, .rmvb, .flv, .mpg, .mkv';
                    var fileTypeText = "上传视频类型错误";
                }
                //模拟出一个input用于添加本地文件
                var input = document.createElement('input');
                input.setAttribute('type', 'file');
                input.setAttribute('accept', fileTypeString);
                input.click();
                input.onchange = function() {
                    var file = this.files[0];
                    var xhr, formData;
                    var videoType = file.name.match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase();
                    if (fileTypeString.indexOf(videoType) == -1) {
                        errorHtml(fileTypeText)
                        return false;
                    }
                    loading(true);
                    console.log(file.name);
                    xhr = new XMLHttpRequest();
                    xhr.withCredentials = false;
                    xhr.open('POST', uploadUrl);
                    xhr.onload = function() {
                        var json;
                        if (xhr.status != 200) {
                            failure('HTTP Error: ' + xhr.status);
                            return;
                        }
                        json = JSON.parse(xhr.responseText);
                        if (!json || typeof json.message != 'string') {
                            failure('Invalid JSON: ' + xhr.responseText);
                            return;
                        }
                        console.log(json.message);
                        // callback(blobInfo.blobUri(), { title: json.message });
                        if (meta.filetype == 'media') { //视频
                            callback(json.message, {
                                source2: 'alt.ogg',
                                poster: json.message + '?vframe/jpg/offset/1',
                            });
                            setTimeout(function () {
                                var inputItem=document.querySelectorAll('.tox-textfield');
                                inputItem[inputItem.length-2].type='number';
                                inputItem[inputItem.length-1].type='number';
                            },500)
                        } else if (meta.filetype == 'image') {
                            callback(json.message, {
                                alt: 'My alt text'
                            });
                            setTimeout(function () {
                                var inputItem=document.querySelectorAll('.tox-textfield');
                                inputItem[inputItem.length-2].type='number';
                                inputItem[inputItem.length-1].type='number';
                            },500)
                        } else if (meta.filetype == 'file') {
                            callback(json.message, {
                                text: 'My text'
                            });
                        }
                        loading(false);
                    };
                    formData = new FormData();
                    formData.append('file', file, file.name);
                    xhr.send(formData);

                };
            },
            autosave_ask_before_unload: false,
            images_upload_handler: function(blobInfo, succFun, failFun) {
                var xhr, formData;
                var file = blobInfo.blob(); //转化为易于理解的file对象
                console.log(file);
                xhr = new XMLHttpRequest();
                xhr.withCredentials = false;
                xhr.open('POST', uploadUrl);
                xhr.onload = function() {
                    var json;
                    if (xhr.status != 200) {
                        failFun('HTTP Error: ' + xhr.status);
                        return;
                    }
                    json = JSON.parse(xhr.responseText);
                    console.log(json);
                    if (!json || typeof json.message != 'string') {
                        failFun('Invalid JSON: ' + xhr.responseText);
                        return;
                    }
                    succFun(json.message);
                    setTimeout(function () {
                        var inputItem=document.querySelectorAll('.tox-textfield');
                        inputItem[inputItem.length-2].type='number';
                        inputItem[inputItem.length-1].type='number';
                    },500)
                };
                formData = new FormData();
                formData.append('file', file, file.name);
                xhr.send(formData);
            },
            importword_handler: function(editor, files, next) {
                var file_name = files[0].name
                if (file_name.substr(file_name.lastIndexOf(".") + 1) == 'docx') {
                    editor.notificationManager.open({
                        text: '正在转换中...',
                        type: 'info',
                        closeButton: false,
                    });
                    next(files);
                } else {
                    editor.notificationManager.open({
                        text: '目前仅支持docx文件格式，若为doc111，请将扩展名改为docx',
                        type: 'warning',
                    });
                }
                // next(files);
            },
            importword_filter: function(result, insert, message) {
                // 自定义操作部分
                insert(result) //回插函数
            }
        });
    }
