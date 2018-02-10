import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { StyleSheet, requireNativeComponent, View, ViewPropTypes } from 'react-native';

import ImageResizeMode from 'react-native/Libraries/Image/ImageResizeMode';
import ImageStylePropTypes from 'react-native/Libraries/Image/ImageStylePropTypes';
import resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource';
import StyleSheetPropType from 'react-native/Libraries/StyleSheet/StyleSheetPropType';

const styles = StyleSheet.create({
  base: {
    overflow: 'hidden',
  },
});

export default class VideoPreview extends Component {

  render() {
    const source = resolveAssetSource(this.props.source);
    const loadingIndicatorSource = resolveAssetSource(this.props.loadingIndicatorSource);

    // As opposed to the ios version, here we render `null` when there is no source, source.uri
    // or source array.

    if (source && source.uri === '') {
      console.warn('source.uri should not be an empty string');
    }

    if (this.props.src) {
      console.warn('The <VideoPreview> component requires a `source` property rather than `src`.');
    }

    if (this.props.children) {
      throw new Error('The <VideoPreview> component cannot contain children. If you want to render content on top of the image, consider using the <ImageBackground> component or absolute positioning.');
    }

    if (source && (source.uri || Array.isArray(source))) {
      let style;
      let sources;
      if (source.uri) {
        const {width, height} = source;
        style = StyleSheet.flatten([{width, height}, styles.base, this.props.style]);
        sources = [{uri: source.uri}];
      } else {
        style = StyleSheet.flatten([styles.base, this.props.style]);
        sources = source;
      }

      const {onLoadStart, onLoad, onLoadEnd, onError} = this.props;
      const nativeProps = Object.assign({}, this.props, {
        style,
        shouldNotifyLoadEvents: !!(onLoadStart || onLoad || onLoadEnd || onError),
        src: sources,
        loadingIndicatorSrc: loadingIndicatorSource ? loadingIndicatorSource.uri : null,
      });

      return <RCTVideoPreview {...nativeProps}/>;
    }
    return null;
  }
};

VideoPreview.propTypes = {
  ...ViewPropTypes,
  style: StyleSheetPropType(ImageStylePropTypes),
  source: PropTypes.oneOfType([
    PropTypes.shape({
      uri: PropTypes.string,
    }),
    PropTypes.number,
    PropTypes.arrayOf(
      PropTypes.shape({
        uri: PropTypes.string,
        width: PropTypes.number,
        height: PropTypes.number,
      }))
  ]),
  blurRadius: PropTypes.number,
  loadingIndicatorSource: PropTypes.oneOfType([
    PropTypes.shape({
      uri: PropTypes.string,
    }),
    PropTypes.number,
  ]),
  progressiveRenderingEnabled: PropTypes.bool,
  fadeDuration: PropTypes.number,
  onLoadStart: PropTypes.func,
  onError: PropTypes.func,
  onLoad: PropTypes.func,
  onLoadEnd: PropTypes.func,
  testID: PropTypes.string,
  resizeMethod: PropTypes.oneOf(['auto', 'resize', 'scale']),
  resizeMode: PropTypes.oneOf(['cover', 'contain', 'stretch', 'center']),
};

const RCTVideoPreview = requireNativeComponent('RCTVideoPreview', VideoPreview, {
  nativeOnly: {
    src: true,
    headers: true,
    loadingIndicatorSrc: true,
    shouldNotifyLoadEvents: true,
  },
});
