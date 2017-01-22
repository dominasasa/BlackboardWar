package com.mySampleApplication.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by abcd on 2017-01-22.
 */

//public class GetPlayerCallback implements AsyncCallback<Player> {
//
//   // Brush result;
//    Board board;
//
//    @Override
//    public void onFailure(Throwable caught) {
//        Window.alert("can't get brush!");
//    }
//
//    @Override
//    public void onSuccess(Player result) {
//        if(result.brush.can_draw && result.brush.down) {
//            Board.this.
//            board.context.beginPath();
//            board.context.moveTo(result.brush.prev_x, result.brush.prev_y);
//            board.context.setLineCap("round");
//            board.context.setFillStyle(result.brush.color);
//            board.context.setLineWidth(5);
//            board.context.lineTo(result.brush.x, result.brush.y);
//            board.context.setStrokeStyle(result.brush.color);
//            board.context.stroke();
//        }
//    }
//
//    GetPlayerCallback(Board board) {
//        this.board = board;
//    }
//}