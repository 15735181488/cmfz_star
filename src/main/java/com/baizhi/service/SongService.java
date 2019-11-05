package com.baizhi.service;

import com.baizhi.entity.Song;

import java.util.Map;

public interface SongService {
    Map<String,Object> selectSongsByAlbumId(Integer page, Integer rows, String albumId);
    String add(Song song);
    void update(Song song);
}
