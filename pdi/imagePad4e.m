function g = imagePad4e(f, r, c, padtype)
% imagePad4e  Pad an image with r rows (top/bottom) and c columns (left/right).
%
% Syntax:
%   g = imagePad4e(f, r, c)
%   g = imagePad4e(f, r, c, padtype)
%
% Inputs:
%   f        - Input image matrix (grayscale MxN or colour MxNx3).
%   r        - Number of rows to add above AND below the image.
%   c        - Number of columns to add to the left AND right.
%   padtype  - (optional) Padding strategy:
%              'zeros'     - fill border with zeros (default).
%              'replicate' - fill border by replicating the nearest edge pixel.
%
% Output:
%   g        - Padded image of size (M+2r) x (N+2c) [x channels].
%              Data type is preserved from the input f.
%
% Examples:
%   f = imread('testpattern1024.tif');
%   g1 = imagePad4e(f, 50, 80);                   % zero padding
%   g2 = imagePad4e(f, 50, 80, 'replicate');       % replicate padding
%
% Author: Pedro Victor dos Santos Oliveira
% Disciplina: Processamento Digital de Imagens – UFAM (2026)

    % ----- argument validation ------------------------------------------------
    if nargin < 3
        error('imagePad4e: at least three arguments (f, r, c) are required.');
    end
    if nargin < 4 || isempty(padtype)
        padtype = 'zeros';
    end
    if ~ismember(padtype, {'zeros', 'replicate'})
        error('imagePad4e: padtype must be ''zeros'' or ''replicate''.');
    end
    if r < 0 || c < 0 || mod(r,1) ~= 0 || mod(c,1) ~= 0
        error('imagePad4e: r and c must be non-negative integers.');
    end
    % --------------------------------------------------------------------------

    [M, N, ch] = size(f);
    newM = M + 2*r;
    newN = N + 2*c;

    % Allocate output (same class as input: uint8, double, etc.)
    g = zeros(newM, newN, ch, class(f));

    % Place original image in the centre of the output
    g(r+1:r+M, c+1:c+N, :) = f;

    if strcmp(padtype, 'replicate')
        % ---- top and bottom horizontal bands (excluding corners) -------------
        g(1:r,        c+1:c+N, :) = repmat(f(1,   :, :), [r, 1, 1]);
        g(r+M+1:newM, c+1:c+N, :) = repmat(f(M,   :, :), [r, 1, 1]);
        % ---- left and right vertical bands (excluding corners) ---------------
        g(r+1:r+M, 1:c,        :) = repmat(f(:,   1, :), [1, c, 1]);
        g(r+1:r+M, c+N+1:newN, :) = repmat(f(:,   N, :), [1, c, 1]);
        % ---- four corners (each replicated from the nearest corner pixel) ----
        g(1:r,        1:c,        :) = repmat(f(1, 1, :), [r, c, 1]);
        g(1:r,        c+N+1:newN, :) = repmat(f(1, N, :), [r, c, 1]);
        g(r+M+1:newM, 1:c,        :) = repmat(f(M, 1, :), [r, c, 1]);
        g(r+M+1:newM, c+N+1:newN, :) = repmat(f(M, N, :), [r, c, 1]);
    end
    % For 'zeros', the output is already initialised to zero – nothing more needed.

end
