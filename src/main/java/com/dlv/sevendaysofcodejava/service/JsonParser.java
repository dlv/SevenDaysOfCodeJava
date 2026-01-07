package com.dlv.sevendaysofcodejava.service;

import com.dlv.sevendaysofcodejava.model.Content;

import java.util.List;

public interface JsonParser {

    List<? extends Content> parse();

}