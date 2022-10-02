/*
 * blueimp Gallery Demo JS
 * https://github.com/blueimp/Gallery
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * https://opensource.org/licenses/MIT
 */

/* global blueimp, $ */

$(function () {
    'use strict'


    $.ajax({
        url: '/getImages',
        method: 'get',
        dataType: 'json',
    }).done(function (result) {
        console.log(result)
        var maxWidth = $(document.body).css('max-width')
        var sizes = '(min-width: ' + maxWidth + ') ' + maxWidth + ', 100vw'
        var carouselLinks = []
        var linksContainer = $('#links')
        // Add the demo images as links with thumbnails to the page:
        console.log(linksContainer)
        $.each(result, function (_, photo) {
            var thumbnail = $('<img>')
                .prop('loading', 'lazy')
                .prop('src', "/images/"+photo.storeFileName)
                .prop('alt', photo.originalFileName)
                .width(100)
                .height(100)
            var srcset = []
            $.each(photo, function (_, type) {
                var url = "/images/"+photo.storeFileName
                var width = "100"
                if (url) {
                    srcset.push(url + ' ' + width + 'w')
                }
                console.log(srcset)
            })
            srcset = srcset.join(',')
            $('<a></a>')
                .append(thumbnail)
                .prop('title', photo.originalFileName)
                .prop('href', "/images/"+photo.storeFileName)
                .attr('data-srcset', srcset)
                .attr('data-gallery', '')
                .appendTo(linksContainer)
            carouselLinks.push({
                title: photo.title,
                href: "/images/"+photo.storeFileName,
                sizes: sizes,
                srcset: srcset
            })
        })

    })



    $('#fullscreen').change(function () {
        $('#blueimp-gallery').data('fullscreen', this.checked)
    })
})
