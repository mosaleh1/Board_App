package com.example.boardapp.model

sealed class BoardActions{

     object ActionPen : BoardActions()
     object ActionArrow : BoardActions()
     object ActionRectangle : BoardActions()
     object ActionCircle : BoardActions()
}
