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

    // Flickr image types:
    var imageTypes = [
        // https://www.flickr.com/services/api/misc.urls.html
        'sq', // 75x75
        'q', // 150x150
        't', // 100 on longest side
        's', // 240 on longest side
        'n', // 320 on longest side
        'm', // 500 on longest side
        'z', // 640 on longest side
        'c', // 800 on longest side
        'l', // 1024 on longest side
        'h', // 1600 on longest side
        'k', // 2048 on longest side
        'o' // original dimensions
    ]

    // Load demo images from Flickr:
    $.ajax({
        url: 'https://api.flickr.com/services/rest/',
        data: {
            // https://www.flickr.com/services/api/flickr.interestingness.getList.html
            method: 'flickr.interestingness.getList',
            format: 'json',
            extras: 'url_' + imageTypes.join(',url_'),
            // eslint-disable-next-line camelcase
            api_key: '7617adae70159d09ba78cfec73c13be3'
        },
        dataType: 'jsonp',
        jsonp: 'jsoncallback'
    }).done(function (result) {
        var maxWidth = $(document.body).css('max-width')
        var sizes = '(min-width: ' + maxWidth + ') ' + maxWidth + ', 100vw'
        var carouselLinks = []
        var linksContainer = $('#links')
        // Add the demo images as links with thumbnails to the page:
        $.each(result.photos.photo, function (_, photo) {
            var thumbnail = $('<img>')
                .prop('loading', 'lazy')
                .prop('width', photo.width_sq)
                .prop('height', photo.height_sq)
                .prop('src', photo.url_sq)
                .prop('alt', photo.title)
            var srcset = []
            $.each(imageTypes, function (_, type) {
                var url = photo['url_' + type]
                var width = photo['width_' + type]
                if (url) {
                    srcset.push(url + ' ' + width + 'w')
                }
            })
            srcset = srcset.join(',')
            $('<a></a>')
                .append(thumbnail)
                .prop('title', photo.title)
                .prop('href', photo.url_l)
                .attr('data-srcset', srcset)
                .attr('data-gallery', '')
                .appendTo(linksContainer)
            carouselLinks.push({
                title: photo.title,
                href: photo.url_l,
                sizes: sizes,
                srcset: srcset
            })
        })

    })



    $('#fullscreen').change(function () {
        $('#blueimp-gallery').data('fullscreen', this.checked)
    })
})
